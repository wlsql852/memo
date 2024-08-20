package com.sparta.memo.repository;

import com.sparta.memo.entity.Memo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemoRepository extends JpaRepository<Memo, Long> {
    //수정일 내림차순 기준으로 모든 메모 조회
    List<Memo> findAllByOrderByModifiedAtDesc();

    //해당 username을 가진 데이터 조회
    List<Memo> findAllByUsername(String username);

    List<Memo> findAllByContentsContainingOrderByModifiedAtDesc(String keyword);

}