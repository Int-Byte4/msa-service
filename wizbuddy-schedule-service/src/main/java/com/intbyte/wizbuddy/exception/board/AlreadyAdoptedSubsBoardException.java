package com.intbyte.wizbuddy.exception.board;

import com.intbyte.wizbuddy.exception.StatusEnum;

public class AlreadyAdoptedSubsBoardException extends IllegalArgumentException{

    private final StatusEnum status;

    private static final String message = "해당 대타 게시글이 존재하지 않습니다.";

    public AlreadyAdoptedSubsBoardException() {
        super(message);
        this.status = StatusEnum.ALREADY_ADOPTED;
    }
}