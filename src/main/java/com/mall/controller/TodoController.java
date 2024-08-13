package com.mall.controller;

import com.mall.dto.common.ResponseDto;
import com.mall.dto.page.PageRequestDto;
import com.mall.dto.page.PageResponseDto;
import com.mall.dto.todo.TodoDto;
import com.mall.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/todo")
public class TodoController {
    private final TodoService todoService;

    @PostMapping
    public ResponseEntity<ResponseDto<Void>> register(@RequestBody TodoDto todoDto) {
        todoService.register(todoDto);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.CREATED, "TODO 생성 완료"), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ResponseDto<PageResponseDto<TodoDto>>> get(PageRequestDto pageRequestDto) {
        PageResponseDto<TodoDto> responseDto = todoService.list(pageRequestDto);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "TODO 전체 조회 성공", responseDto), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<TodoDto>> get(@PathVariable(name = "id") UUID tno) {
        TodoDto todoDto = todoService.get(tno);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "TODO 단건 조회 성공", todoDto), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto<Void>> modify(@PathVariable(name = "id") UUID tno,
                                                    @RequestBody TodoDto todoDto) {
        todoService.modify(tno, todoDto);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "TODO 단건 수정 성공"), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<Void>> remove(@PathVariable(name = "id") UUID tno) {
        todoService.remove(tno);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "TODO 단건 삭제 성공"), HttpStatus.OK);
    }
}
