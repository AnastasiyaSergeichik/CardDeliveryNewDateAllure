package ru.netology;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MeetingInfo {
    private final String name;
    private final String city;
    private final String phone;
}
