package com.sparta.memo.controller;

import com.sparta.memo.dto.MemoRequestDto;
import com.sparta.memo.dto.MemoResponseDto;
import com.sparta.memo.entity.Memo;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api")
public class MemoController {

    private final Map<Long, Memo> memolist = new HashMap<>();

    @PostMapping("/memos")
    public MemoResponseDto createMemo(@RequestBody MemoRequestDto requestDto) {
        //RequestDto -> Entity
        Memo memo = new Memo(requestDto);

        //Memo Max ID check
        Long maxId = memolist.size() > 0 ? Collections.max(memolist.keySet())+1 :1 ;
        memo.setId(maxId);

        //DB저장
        memolist.put(memo.getId(), memo);

        //Entity -> ResponseDto
        MemoResponseDto memoResposeDto = new MemoResponseDto(memo);

        return memoResposeDto;
    }

    @GetMapping("/memos")
    public List<MemoResponseDto> getMemos() {
        //Map To List
        List<MemoResponseDto> responseList = memolist.values().stream()
                .map(MemoResponseDto::new).toList();
        return responseList;
    }
}
