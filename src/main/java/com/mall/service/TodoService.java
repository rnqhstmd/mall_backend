package com.mall.service;

import com.mall.domain.Todo;
import com.mall.dto.page.PageRequestDto;
import com.mall.dto.page.PageResponseDto;
import com.mall.dto.todo.TodoDto;
import com.mall.exception.ErrorCode;
import com.mall.exception.NotFoundException;
import com.mall.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TodoService {
    private final TodoRepository todoRepository;
    private final ModelMapper modelMapper;

    public UUID register(TodoDto todoDto) {
        Todo todo = modelMapper.map(todoDto, Todo.class);
        Todo save = todoRepository.save(todo);
        return save.getId();
    }

    public TodoDto get(UUID tno) {
        Todo todo = findExistingTodo(tno);
        TodoDto dto = modelMapper.map(todo, TodoDto.class);
        return dto;
    }

    public void modify(UUID tno, TodoDto todoDto) {
        Todo todo = findExistingTodo(tno);

        todo.changeTitle(todoDto.getTitle());
        todo.changeDueDate(todoDto.getDueDate());
        todo.changeComplete(todoDto.isComplete());

        todoRepository.save(todo);
    }

    private Todo findExistingTodo(UUID tno) {
        return todoRepository.findById(tno)
                .orElseThrow(() -> new NotFoundException(ErrorCode.TODO_NOT_FOUND));
    }

    public void remove(UUID tno) {
        Todo todo = findExistingTodo(tno);
        todoRepository.deleteById(todo.getId());
    }

    public PageResponseDto<TodoDto> list(PageRequestDto pageRequestDto) {
        Pageable pageable = PageRequest.of(
                pageRequestDto.getPage() - 1,
                pageRequestDto.getSize(),
                Sort.by("createdAt").descending());
        Page<Todo> todoList = todoRepository.findAll(pageable);

        List<TodoDto> dtoList = todoList.getContent().stream()
                .map(todo -> modelMapper.map(todo, TodoDto.class))
                .collect(Collectors.toList());

        long totalCount = todoList.getTotalElements();

        PageResponseDto<TodoDto> responseDto = PageResponseDto.<TodoDto>withAll()
                .dtoList(dtoList)
                .pageRequestDTO(pageRequestDto)
                .totalCount(totalCount)
                .build();

        return responseDto;
    }
}
