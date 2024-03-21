# SecretSanta
# Приложение тайный санта 

## Для начала работы:

### 1. Склонировать репозиторий

```
git clone https://github.com/Farlle/SecretSanta.git
```

### 2. Указать в файле docker-compose.yml 

  - spring.datasource.password = пароль пользователя бд
  - spring.datasource.username = имя пользователя бд
  - token= токен от телеграм бота

### 3. Запустить docker-compose.yml в директории проекта
```
docker-compose up
```
### 4. Открыть в браузере: <a href="http://localhost:8080/home">8080/home<a>

## ВАЖНОЕ

- При регистрации надо вводить свой телеграм ник и написать сообщение боту для полного функционала программы
- База на удаленном сервере
- Пароли и токены брать у https://t.me/farl1e
- Приглашать в комнату можно только зарегестрированных участников, которые написали боту
- Некоторые зарегистрированные пользователи:
  ```
  Name: asd Password: asd
  Name Alexander Password: 123
  ```
  Чтобы протестировать уведомления через тг, можно за пользователя asd создать комнаты и пригласить туда самого себя (предварительно введя при регистрации свой тг ник)
