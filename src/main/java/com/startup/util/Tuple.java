package com.startup.util;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Tuple <T1, T2>{
    private T1 first;
    private T2 second;
}
