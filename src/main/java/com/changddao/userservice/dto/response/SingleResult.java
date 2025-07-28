package com.changddao.userservice.dto.response;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class SingleResult<T> extends Result{
    T data;
}
