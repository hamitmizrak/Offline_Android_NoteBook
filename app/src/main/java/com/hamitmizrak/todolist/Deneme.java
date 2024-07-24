package com.hamitmizrak.todolist;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Deneme {
    private String name;
    private String surname;

    public static void main(String[] args) {
        Deneme deneme= Deneme.builder().name("Hamit").surname("Mızrak").build();
        System.out.println(deneme);
    }
}
