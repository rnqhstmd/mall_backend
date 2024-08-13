package com.mall.service;

import com.mall.dto.page.PageRequestDto;
import com.mall.dto.page.PageResponseDto;
import com.mall.dto.todo.TodoDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.UUID;

@Slf4j
@SpringBootTest
class TodoServiceTest {
    @Autowired
    private TodoService todoService;

    @Test
    void register() {
        TodoDto todoDTO = TodoDto.builder()
                .title("서비스 테스트")
                .writer("tester")
                .dueDate(LocalDate.of(2023, 10, 10))
                .build();
        UUID tno = todoService.register(todoDTO);
        log.info("TNO : " + tno);
    }

    @Test
    void list() {
        PageRequestDto pageRequestDTO = PageRequestDto.builder()
                .page(2)
                .size(10)
                .build();

        PageResponseDto<TodoDto> response = todoService.list(pageRequestDTO);
        log.info(response.getDtoList().toString());
    }
}