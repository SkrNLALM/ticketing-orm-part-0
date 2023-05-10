package com.cydeo.service.impl;

import com.cydeo.dto.UserDTO;
import com.cydeo.entity.User;
import com.cydeo.mapper.UserMapper;
import com.cydeo.repository.UserRepository;
import com.cydeo.service.UserService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service

public class UserServiceImpl implements UserService {

   private final UserRepository userRepository;
   private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public List<UserDTO> listAllUsers() {

        //controller calling me and requesting all the roles
        //so .. I need to go to database and bring all the roles from there

        List<User> userList =userRepository.findAll(Sort.by("firstName"));
        return userList.stream().map(userMapper::convertToDTO).collect(Collectors.toList());
     //   return userRepository.findAll(Sort.by("firstName")).stream().map(userMapper::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public UserDTO findByUserName(String username) {   //here we do not have stream because there is only one object
        User user=userRepository.findByUserName(username);
        return userMapper.convertToDTO(user);
    }

    @Override
    public void save(UserDTO dto) {
        userRepository.save(userMapper.convertToEntity(dto));

    }

    @Override
    public UserDTO update(UserDTO dto) {
        //find current user
        User user = userRepository.findByUserName(dto.getUserName());
        //Map updated user dto entity object
        User comnvertedUser = userMapper.convertToEntity(dto);
        //set id to converted object
        comnvertedUser.setId(user.getId());
        //save the updated user
        userRepository.save(comnvertedUser);

        return findByUserName(dto.getUserName());
    }
    @Override
    public void deleteByUserName (String username) {

        userRepository.deleteByUserName(username);
    }
    @Override
    public void delete(String username) {
        //I will not delete from db, change the flag and keep it in the db
     User user=userRepository.findByUserName(username);
     user.setIsDeleted(true);
     userRepository.save(user);

    }

    @Override
    public List<UserDTO> listAllByRole(String role) {
        List<User> users =userRepository.findAllByRoleDescriptionIgnoreCase(role);
        return users.stream().map(userMapper::convertToDTO).collect(Collectors.toList());
    }
}
