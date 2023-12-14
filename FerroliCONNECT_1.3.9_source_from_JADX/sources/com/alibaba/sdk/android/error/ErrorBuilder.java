package com.alibaba.sdk.android.error;

import java.util.ArrayList;

public class ErrorBuilder {
    private Code code = null;
    private String detail = null;
    private String msg = null;
    private ArrayList<String> solutions = new ArrayList<>();

    private ErrorBuilder(Code code2) {
        this.code = code2;
    }

    public static ErrorBuilder builder(Code code2) {
        return new ErrorBuilder(code2);
    }

    public ErrorCode build() {
        ErrorCode errorCode = new ErrorCode(this.code);
        String str = this.msg;
        if (str != null) {
            errorCode.setMsg(str);
        }
        String str2 = this.detail;
        if (str2 != null) {
            errorCode.setDetail(str2);
        }
        if (this.solutions.size() > 0) {
            errorCode.setSolutions((String[]) this.solutions.toArray(new String[0]));
        }
        return errorCode;
    }

    public ErrorBuilder detail(String str) {
        this.detail = str;
        return this;
    }

    public ErrorBuilder msg(String str) {
        this.msg = str;
        return this;
    }

    public ErrorBuilder solution(String str) {
        this.solutions.add(str);
        return this;
    }

    public ErrorBuilder solutions(String[] strArr) {
        this.solutions.clear();
        for (String add : strArr) {
            this.solutions.add(add);
        }
        return this;
    }
}
