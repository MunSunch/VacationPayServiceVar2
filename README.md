[![codecov](https://codecov.io/gh/MunSunch/VacationPayServiceVar2/graph/badge.svg?token=EZ44G3V576)](https://codecov.io/gh/MunSunch/VacationPayServiceVar2) 
![GitHub stars](https://img.shields.io/github/stars/MunSunch/VacationPayServiceVar2)
![GitHub commit activity](https://img.shields.io/github/commit-activity/w/MunSunch/VacationPayServiceVar2)
![GitHub watchers](https://img.shields.io/github/watchers/MunSunch/VacationPayServiceVar2)
[![SonarCloud](https://sonarcloud.io/images/project_badges/sonarcloud-black.svg)](https://sonarcloud.io/summary/new_code?id=MunSunch_VacationPayServiceVar2)

# Приложение "Калькулятор отпускных". Микросервис на SpringBoot + Java 11
Проект удаленно развернут на ВМ в yandex cloud: http://51.250.74.226:8080/api-docs-ui
Для мониторинга сервиса развернут Prometheus+Grafana на raspberry pi 4B, к сожалению, доступно только во внутренней сети из-за проблем с провайдером. Скриншоты дашбордов в конце документа.

## Минимальные требования
Приложение принимает твою среднюю зарплату за 12 месяцев и количество дней отпуска - отвечает суммой отпускных, которые придут сотруднику.
Доп. задание: При запросе также можно указать точные дни ухода в отпуск, тогда должен проводиться рассчет отпускных с учётом праздников и выходных.

## Реализация
Разработка выполнялась с применением ci/cd пайплайнов на ветках /dev и /main. В первой проверялось покрытие тестами, в
последнем - пуш образа в удаленный регистр. Сам проект развернут на ВМ в yandex cloud, который своевременно реагирует на обновления
образа. Ссылку ищи выше.

Поначалу была идея получать производственный календарь через запросы на другой сервер, позднее - отказался
в пользу хранения календаря в файловой системе. 

При вычислении отпускных по датам праздники не учитываются. Подоходный налог-13%, среднее число дней в 1 месяце - 29,3.

Документация доступна по адресу /api-docs-ui и /api-docs.

## Запуск локальный
```
git clone https://github.com/MunSunch/VacationPayServiceVar2.git
cd VacationPayServiceVar2
docker compose up -d
echo "start application: http://localhost:8080/api-docs-ui"
```

## Метрики

![](https://downloader.disk.yandex.ru/preview/df60eb5a47e8a810bb40bb9f6ef8d330caedb038c71feb81de772f7beeae7464/66fc199a/mSjVRF9fjuDqKSJ7_CqdkTHuIAsz0HO4O3VAwhL3_3mdKPqrsjhoQQnvULhGRVD9t9qudBGGWyNgXl3Qj4TJ7A%3D%3D?uid=0&filename=metrics.png&disposition=inline&hash=&limit=0&content_type=image%2Fpng&owner_uid=0&tknv=v2&size=2048x2048)
