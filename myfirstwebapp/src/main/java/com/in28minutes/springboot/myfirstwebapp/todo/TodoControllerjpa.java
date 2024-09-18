package com.in28minutes.springboot.myfirstwebapp.todo;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import jakarta.validation.Valid;

@Controller
@SessionAttributes("name")
public class TodoControllerjpa {
	
	public TodoControllerjpa(TodoRepository todoRepository) {
		super();		
		this.todoRepository = todoRepository; //이렇게 하면 자동적으로 결합(생성자)
	}
		
	//DB 테이블과 소통하기 위한 선언. -> 생성자 인수로 추가
	private TodoRepository todoRepository;

	// /list-todos
	@RequestMapping("list-todos")
	public String listAllTodos(ModelMap model) {
		String username = getLoggedInUsername(model);		
		
		List<Todo> todos = todoRepository.findByUsername(username);
		model.addAttribute("todos",todos);
		
		return "listTodos";
	}

	
	//GET
	@RequestMapping(value="add-todo", method=RequestMethod.GET)
	public String showNewTodoPage(ModelMap model) {
		String username = getLoggedInUsername(model);
		Todo todo = new Todo(0, username, "", LocalDate.now().plusYears(1), false);
		model.put("todo", todo);
		return "todo";		
	}
	
	//POST
	//@RequestParam String description을 사용하지 않고 Todo의 Description을 가져오도록
	@RequestMapping(value="add-todo", method=RequestMethod.POST)
	public String addNewTodo(ModelMap model, @Valid Todo todo, BindingResult result) {
		
		if(result.hasErrors()) {
			return "todo";
		}
		
		String username = getLoggedInUsername(model);
		todo.setUsername(username);//Todo Bean 넣기
		todoRepository.save(todo);//왜냐 todoRepository는 모든 속성을 받는 메서드가 없기 때문
		
		//todoService.addTodo(username, todo.getDescription(), todo.getTargetDate(), todo.isDone());
		
		return "redirect:list-todos";			
	}
	
	@RequestMapping("delete-todo")
	public String deleteTodo(@RequestParam int id) {
		todoRepository.deleteById(id);
		//todoService.deleteById(id);
		return "redirect:list-todos";
	}
	
	@RequestMapping(value="update-todo", method=RequestMethod.GET)
	public String showUpdateTodo(@RequestParam int id,ModelMap model) {
		Todo todo = todoRepository.findById(id).get();
		model.addAttribute("todo", todo);//model에 todo추가
		return "todo";
	}
	
	@RequestMapping(value="update-todo", method=RequestMethod.POST)
	public String updateTodo(ModelMap model, @Valid Todo todo, BindingResult result) {
		
		if(result.hasErrors()) {
			return "todo";
		}
		
		String username = getLoggedInUsername(model);
		todo.setUsername(username);
		todoRepository.save(todo);
		//todoService.updateTodo(todo);		
		
		return "redirect:list-todos";			
	}
	
	private String getLoggedInUsername(ModelMap model) {
		return (String)model.get("name");
	}
	

}