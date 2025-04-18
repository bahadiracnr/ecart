package com.ecart.exceptioncommon.handler;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Exception<E> {

    private String path;

    private Date createTime;

    private String hostName;

    private E message;
}
