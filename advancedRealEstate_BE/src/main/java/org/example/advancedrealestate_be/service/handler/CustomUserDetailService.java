//package org.example.advancedrealestate_be.service.handler;
//
//
//import org.example.advancedrealestate_be.dto.MyUserDetail;
//import org.example.advancedrealestate_be.dto.RoleDto;
//import org.example.advancedrealestate_be.dto.UserDto;
//import org.example.advancedrealestate_be.service.UserService;
//import org.springframework.beans.BeanUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//public class CustomUserDetailService implements UserDetailsService {
//
//    @Autowired
//    private UserService userService;
//
////    @Override
////    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
////        UserDto userDTO = userService.findOneByUserNameAndStatus(name, 1);
////        if(userDTO == null){
////            throw new UsernameNotFoundException("Username not found");
////        }
////        List<GrantedAuthority> authorities = new ArrayList<>();
////        for(RoleDto role: userDTO.getRoles()){
////            authorities.add(new SimpleGrantedAuthority("ROLE_"+role.getCode()));
////        }
////        MyUserDetail myUserDetail = new MyUserDetail(name,userDTO.getPassword(),true,true,true,true,authorities);
////        BeanUtils.copyProperties(userDTO, myUserDetail);
////        return myUserDetail;
////    }
//
//    @Override
//    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
//        UserDto userDTO = userService.findOneByUserNameAndStatus(name, 1);
//        if (userDTO == null) {
//            throw new UsernameNotFoundException("Username not found");
//        }
//        List<GrantedAuthority> authorities = new ArrayList<>();
//        for (RoleDto role : userDTO.getRoles()) {
//            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getCode()));
//        }
//        MyUserDetail myUserDetail = new MyUserDetail(name, userDTO.getPassword(), true, true, true, true, authorities);
//        BeanUtils.copyProperties(userDTO, myUserDetail);
//        return myUserDetail;
//    }
//
//}
