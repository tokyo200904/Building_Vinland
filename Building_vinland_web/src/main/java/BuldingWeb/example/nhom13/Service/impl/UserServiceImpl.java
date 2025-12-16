package BuldingWeb.example.nhom13.Service.impl;

import BuldingWeb.example.nhom13.Entity.User;
import BuldingWeb.example.nhom13.Enums.VaiTro;
import BuldingWeb.example.nhom13.Model.Reponse.UserDnReponse;
import BuldingWeb.example.nhom13.Model.Reponse.UserReponse;
import BuldingWeb.example.nhom13.Model.UserDkDTO;
import BuldingWeb.example.nhom13.Model.UserDnDTO;
import BuldingWeb.example.nhom13.Repository.UserReponsitory;
import BuldingWeb.example.nhom13.Service.UserService;
import BuldingWeb.example.nhom13.Utils.TtNdUtil;
import BuldingWeb.example.nhom13.components.jwtTokenUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserReponsitory userReponsitory;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private jwtTokenUtil jwtTokenUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TtNdUtil ttNdUtil;

        @Override
        public User createUser(UserDkDTO userDkDTO) {
            String email = userDkDTO.getEmail();
            if (userReponsitory.existsByEmail(email)) {
                throw new RuntimeException("Email da ton tai");
            }
            User newUser = User.builder()
                    .hoTen(userDkDTO.getHoTen())
                    .email(userDkDTO.getEmail())
                    .vaiTro(VaiTro.CUSTOMER)
                    .matKhau(passwordEncoder.encode(userDkDTO.getMatKhau()))
                    .build();
            return userReponsitory.save(newUser);
        }
        @Override
        public UserDnReponse loginUser(UserDnDTO userDnDTO) throws Exception {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userDnDTO.getEmail(),
                            userDnDTO.getMatKhau()
                    )
            );
            User userEntity = (User) authentication.getPrincipal();

            String token = jwtTokenUtil.generateToken(userEntity);

            return UserDnReponse.builder()
                    .token(token)
                    .hoTen(userEntity.getHoTen())
                    .email(userEntity.getEmail())
                    .vaiTro(userEntity.getVaiTro())
                    .anhdaiDien(userEntity.getAnhDaiDien())
                    .build();
        }

    @Override
    public UserReponse findUserDTOById(Integer userId) {
        User user = userReponsitory.findById(userId).orElseThrow(() ->new RuntimeException("khong tim thay user"));
        return UserReponse.fromEntity(user);
    }

    @Transactional
    @Override
    public void updateUserRole(Integer adminUserId, Integer targetUserId, String newRoleString) throws Exception {
        User adminUser = userReponsitory.findById(adminUserId).get();
        User targatUser = userReponsitory.findById(targetUserId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));
        VaiTro newRole;
        try {
            newRole = VaiTro.valueOf(newRoleString.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new Exception("Vai trò mới không hợp lệ: " + newRoleString);
        }
        if (adminUser.getUserId().equals(targatUser.getUserId())) {
            throw new AccessDeniedException("Bạn không thể tự thay đổi vai trò của chính mình.");
        }

        if (targatUser.getVaiTro() == VaiTro.ADMIN) {
            throw new AccessDeniedException("Không thể thay đổi vai trò của một Admin khác.");
        }
        if (newRole == VaiTro.ADMIN) {
            throw new AccessDeniedException("Không thể cấp quyền Admin thông qua chức năng này.");
        }
        targatUser.setVaiTro(newRole);
        userReponsitory.save(targatUser);
    }

    @Transactional
    @Override
    public void banUser(Integer adminUserId, Integer targetUserId) throws Exception {
            User adminUser = userReponsitory.findById(adminUserId).orElseThrow(()->new RuntimeException("khong tim thay user"));
            User usertarget = userReponsitory.findById(targetUserId).orElseThrow(() ->new RuntimeException("khong tim thay User"));
            if (adminUser.getUserId().equals(usertarget.getUserId())){
                throw new AccessDeniedException("khong cam duoc chinh minh");
            }
            if (usertarget.getVaiTro() == VaiTro.ADMIN){
                throw new AccessDeniedException("Không thể cấm một tài khoản Admin.");
            }
            usertarget.setBanned(true);
            userReponsitory.save(usertarget);
    }
    @Transactional
    @Override
    public void unbanUser(Integer targetUserId) throws Exception {
            User targetUser = userReponsitory.findById(targetUserId).orElseThrow(() -> new Exception("khong tim thay user"));
            targetUser.setBanned(false);
            userReponsitory.save(targetUser);
    }
    @Override
    public List<UserReponse> findUsersByRoleAndSearch(VaiTro role, String search) {
        String search1 = (search == null)? "" : search;
        List<User> users = userReponsitory.findByVaiTroAndSearch(role,search1);
        return users.stream()
                .map(UserReponse:: fromEntity)
                .collect(Collectors.toList());
    }

}