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

    @PutMapping("/memos/{id}")
    public Long updateMemo(@PathVariable Long id, @RequestBody MemoRequestDto requestDto) throws IllegalAccessException {
        //해당 메모가 DB에 존재하는지 확인
        if(memolist.containsKey(id)) {
            //해당 메모 가져오기
            Memo memo = memolist.get(id);

            //memo 수정
            memo.update(requestDto);
            return memo.getId();
        }else {
            throw new IllegalAccessException("선택한 메모는 존재하지 않습니다");
        }
    }

    @DeleteMapping("/memos/{id}")
    public Long deleteMemo(@PathVariable Long id) throws IllegalAccessException {
        //해당 메모가 DB에 존재하는지 확인
        if(memolist.containsKey(id)) {
            memolist.remove(id);
            return id;
        } else {
            throw new IllegalAccessException("선택한 메모는 존재하지 않습니다");
        }
    }
}
