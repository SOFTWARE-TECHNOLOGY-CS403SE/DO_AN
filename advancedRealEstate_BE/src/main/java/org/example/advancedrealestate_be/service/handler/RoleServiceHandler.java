package org.example.advancedrealestate_be.service.handler;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.advancedrealestate_be.dto.request.CreateRolePermissionRequest;
import org.example.advancedrealestate_be.dto.request.DeleteRoleRequest;
import org.example.advancedrealestate_be.dto.request.RoleCreationRequest;
import org.example.advancedrealestate_be.dto.request.RoleUpdateRequest;
import org.example.advancedrealestate_be.dto.response.BuildingResponse;
import org.example.advancedrealestate_be.dto.response.RoleResponse;
import org.example.advancedrealestate_be.entity.Building;
import org.example.advancedrealestate_be.entity.Permission;
import org.example.advancedrealestate_be.entity.Role;
import org.example.advancedrealestate_be.exception.AppException;
import org.example.advancedrealestate_be.exception.ErrorCode;
import org.example.advancedrealestate_be.mapper.RoleMapper;
import org.example.advancedrealestate_be.mapper.RoleMapperImpl;
import org.example.advancedrealestate_be.repository.PermissionRepository;
import org.example.advancedrealestate_be.repository.RoleRepository;
import org.example.advancedrealestate_be.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j

public class RoleServiceHandler implements RoleService {
    @Autowired
    RoleRepository roleRepository;

    @Autowired
    RoleMapper roleMapper;

    @Autowired
    PermissionRepository permissionRepository;

    public RoleServiceHandler(RoleRepository roleRepository, RoleMapper roleMapper, PermissionRepository permissionRepository) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
        this.permissionRepository = permissionRepository;
    }

    @Override
    public String createRole(RoleCreationRequest request) {
        Role role = roleMapper.toRequest(request);
        try {
            roleRepository.save(role);
        } catch (DataIntegrityViolationException exception) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        return "Đã thêm mới thành công";
    }

    @Override
    public String createRolePermission(CreateRolePermissionRequest request) {
        Role role = roleRepository.findById(request.getRoleId())
                .orElseThrow(() -> new RuntimeException("Role not found"));

        Permission permission = permissionRepository.findById(String.valueOf(request.getPermissionId()))
                .orElseThrow(() -> new RuntimeException("Permission not found"));

        // Thêm Permission vào Role
        role.getPermissions().add(permission);

        // Lưu thay đổi
        roleRepository.save(role);

        return "Đã thêm mới thành công";
    }

    @Override
    public String updateRole(String userId, RoleUpdateRequest request) {

        Role role = roleRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy Role với ID: " + userId));

        roleMapper.toUpdateRequest(role, request);
        roleRepository.save(role);
        return "Đã cập nhật thành công";
    }

    @Override
    public String deleteRole(String RoleId) {
        Role role = roleRepository.findById(RoleId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy Role với ID: " + RoleId));

        roleRepository.delete(role);
        return "Đã xóa thành công";
    }

    @Override
    public Page<RoleResponse> getBuilding(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Role> rolePage = roleRepository.findAll(pageable);

        List<RoleResponse> roleResponses = rolePage.getContent().stream()
                .map(roleMapper::toResponse)
                .collect(Collectors.toList());

        // Tạo đối tượng Page<TypeBuildingResponse> từ List<TypeBuildingResponse> và thông tin phân trang của Page<User>
        return new PageImpl<>(roleResponses, pageable, rolePage.getTotalElements());
    }

    @Override
    public String deleteRoles(DeleteRoleRequest request) {
        for (String id : request.getIds()) {
            if (roleRepository.existsById(id)) {
                roleRepository.deleteById(id);
            } else {
                throw new RuntimeException("Role with ID " + id + " does not exist");
            }
        }
        return "Deleted successfully!";
    }

    @Override
    public List<RoleResponse> getAllRoles() {
        return roleRepository.findAll().stream().map(roleMapper::toResponse).collect(Collectors.toList());
    }
}
