package com.mall.dto.page;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Getter
@AllArgsConstructor
public class PageResponseDto<E> {
    private List<E> dtoList;
    private List<Integer> pageNumList;
    private PageRequestDto pageRequestDTO;
    private boolean prev, next;
    private int totalCount, prevPage, nextPage, totalPage, current;

    @Builder(builderMethodName = "withAll")
    public PageResponseDto(List<E> dtoList, PageRequestDto pageRequestDTO, long totalCount) {
        this.dtoList = dtoList;
        this.pageRequestDTO = pageRequestDTO;
        this.totalCount = (int) totalCount;

        int end = (int) (Math.ceil(pageRequestDTO.getPage() / 10.0)) * 10;
        int start = end - 9;
        int last = (int) (Math.ceil((totalCount / (double) pageRequestDTO.getSize())));
        end = end > last ? last : end;

        this.prev = start > 1;
        this.next = totalCount > end * pageRequestDTO.getSize();
        this.pageNumList = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());
        if (prev)
            this.prevPage = start - 1;
        if (next)
            this.nextPage = end + 1;
        this.totalPage = this.pageNumList.size();
        this.current = pageRequestDTO.getPage();
    }

    public static class PageResponseDtoBuilder<E> {
        public PageResponseDto<E> build() {
            int end = (int) (Math.ceil(this.pageRequestDTO.getPage() / 10.0)) * 10;
            int start = end - 9;
            int last = (int) (Math.ceil((this.totalCount / (double) this.pageRequestDTO.getSize())));
            end = Math.min(end, last);

            boolean prev = start > 1;
            boolean next = this.totalCount > end * this.pageRequestDTO.getSize();
            List<Integer> pageNumList = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());
            int totalPage = pageNumList.size();
            int current = this.pageRequestDTO.getPage();
            int prevPage = prev ? start - 1 : -1;
            int nextPage = next ? end + 1 : -1;

            return new PageResponseDto<>(dtoList, pageNumList, pageRequestDTO, prev, next, (int) totalCount, prevPage, nextPage, totalPage, current);
        }
    }
}
