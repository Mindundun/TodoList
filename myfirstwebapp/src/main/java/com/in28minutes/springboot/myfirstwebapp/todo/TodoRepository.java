package com.in28minutes.springboot.myfirstwebapp.todo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Integer>{ //Todo라는 Bean, ID의 자료형
	public List<Todo> findByUsername(String username); //username로 값을 찾을 때 
}
