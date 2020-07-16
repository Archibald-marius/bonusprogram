# bonusprogram

Class SiteUsers - список юзерів, зазначено юзер першої, другої та третьої лінії.
Class BonusProgram - відповідність кожного юзера по програмі. Тут зазначено чи може юзер отримати бонус по конктерній ліній, та зберігаються тимчасові даня допоки у коистувача не відкриється нова лінія.
Логіка розподілу в класі BonusProgramService. При збереженні кожної транзакції відбувається перевірка по дереву користувачів і розподіл коштів.

Для запуску необхідно змінити конфігурації на свої в application.properites:
spring.datasource.url=jdbc:mysql://localhost:3306/НАЗВА_БАЗИ_ДАННИХ?useSSL=false
spring.datasource.username=логі до свого SQL
spring.datasource.password=пароль до SQL
