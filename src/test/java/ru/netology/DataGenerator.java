package ru.netology;

import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class DataGenerator {
    private DataGenerator() {
    }

    public static class Registration {
        private Registration() {
        }

        public static MeetingInfo generateMeetingInfo(String locale) {
            Faker faker = new Faker(new Locale(locale));
            return new MeetingInfo(
                    faker.name().lastName() + " " + faker.name().firstName(),
                    generateCity(),
                    faker.phoneNumber().phoneNumber()
            );
        }

        public static String generateDate(int days) {
            String date = LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
            return date;
        }

        public static String generateCity() {
            Random rand = new Random();
            List<String> cityList = Arrays.asList("Майкоп", "Горно-Алтайск", "Уфа", "Улан-Удэ", "Махачкала", "Магас", "Нальчик", "Элиста", "Черкесск", "Петрозаводск", "Сыктывкар", "Симферополь", "Йошкар-Ола", "Саранск", "Якутск", "Владикавказ", "Казань", "Кызыл", "Ижевск", "Абакан", "Грозный", "Чебоксары", "Края", "Барнаул", "Чита", "Петропавловск-Камчатский", "Краснодар", "Красноярск", "Пермь", "Владивосток", "Ставрополь", "Хабаровск", "Благовещенск", "Архангельск", "Астрахань", "Белгород", "Брянск", "Владимир", "Волгоград", "Вологда", "Воронеж", "Иваново", "Иркутск", "Калининград", "Калуга", "Кемерово", "Киров", "Кострома", "Курган", "Курск", "Санкт-Петербург", "Липецк", "Магадан", "Москва", "Красногорск", "Мурманск", "Нижний Новгород", "Великий Новгород", "Новосибирск", "Омск", "Оренбург", "Орёл", "Пенза", "Псков", "Ростов-на-Дону", "Рязань", "Самара", "Саратов", "Южно-Сахалинск", "Екатеринбург", "Смоленск", "Тамбов", "Тверь", "Томск", "Тула", "Тюмень", "Ульяновск", "Севастополь", "Нарьян-Мар", "Ханты-Мансийск", "Анадырь", "Салехард");
            String city = cityList.get(rand.nextInt(cityList.size()));
            return city;

        }

        public static String generateInvalidCity() {
            Faker faker = new Faker(new Locale("en"));
            return faker.address().city();
        }

        public static String generateInvalidPhone() {
            Random rand = new Random();
            List<String> phoneList = Arrays.asList("+34", "58585858585", "+5676");
            String phone = phoneList.get(rand.nextInt(phoneList.size()));

            return phone;
        }

    }
}

