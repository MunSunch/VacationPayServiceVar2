# Приложение "Калькулятор отпускных". Микросервис на SpringBoot + Java 11
Проект удаленно развернут: http://51.250.74.226:8080/api-docs-ui

## Минимальные требования
Приложение принимает твою среднюю зарплату за 12 месяцев и количество дней отпуска - отвечает суммой отпускных, которые придут сотруднику.
Доп. задание: При запросе также можно указать точные дни ухода в отпуск, тогда должен проводиться рассчет отпускных с учётом праздников и выходных.

## Реализация
Разработка выполнялась с применением ci/cd пайплайнов на ветках /dev и /main. В первой проверялось покрытие тестами, в
последнем - пуш образа в удаленный регистр. Сам проект развернут на ВМ в yandex cloud, который своевременно реагирует на обновления
образа. Ссылку ищи выше.

Поначалу была идея получать производственный календарь через запросы на другой сервер, позднее - отказался
в пользу хранения календаря в файловой системе. 

Документация доступна по адресу /api-docs-ui и /api-docs.

## Запуск локальный
```
git clone https://github.com/MunSunch/VacationPayServiceVar2.git
cd VacationPayServiceVar2
docker compose up -d
```
