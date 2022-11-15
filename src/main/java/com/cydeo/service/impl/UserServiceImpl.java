package com.cydeo.service.impl;

import com.cydeo.dto.UserDTO;
import com.cydeo.entity.User;
import com.cydeo.mapper.UserMapper;
import com.cydeo.repository.UserRepository;
import com.cydeo.service.UserService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;



    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public List<UserDTO> listAllUsers() {
        List<User> userList=userRepository.findAll(Sort.by("firstName"));

        return userList.stream().map(userMapper::convertToDto).collect(Collectors.toList());
    }

    @Override
    public UserDTO findByUserName(String userName) {


        User user=userRepository.findByUserName(userName);
        return userMapper.convertToDto(user);
    }

    @Override
    public void save(UserDTO dto) {
        userRepository.save(userMapper.convertToEntity(dto));
    }

    @Override
    public UserDTO update(UserDTO dto) {
        //Find current user
        User user=userRepository.findByUserName(dto.getUserName());
        //MAp update user dto to entity object
        User convertedUser=userMapper.convertToEntity(dto);
        //set id to converted object
        convertedUser.setId(user.getId());
        //save updated user
        userRepository.save(convertedUser);

        return findByUserName(dto.getUserName());
    }

    @Override
    public void deleteByUserName(String username) {
        userRepository.deleteByUserName(username);

    }

    @Override
    public void delete(String userName) {
        //i ll not delete from database
        //change the flag and keep it in the db
        User user=userRepository.findByUserName(userName);
        //user.setDeleted(true);
        user.setIsDeleted(true);
        userRepository.save(user);

    }

    @Override
    public List<UserDTO> listAllByRole(String role) {
        List<User> users=userRepository.findAllByRoleDescriptionIgnoreCase(role);
        return users.stream().map(userMapper::convertToDto).collect(Collectors.toList());
    }
}
