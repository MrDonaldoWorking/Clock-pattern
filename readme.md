# Лабораторная 8.
Цель: получить практический опыт применения паттерна Clock при реализации тестов.

Необходимо реализовать интерфейс EventsStatistic, который считает события, происходящие в системе. Реализация должна хранить статистику ровно за последний час и подсчитывать, сколько событий каждого типа произошло в минуту.

Интерфейс `EventsStatistic`:
```java
public interface EventsStatistic {
    void incEvent(String name);
    ... getEventStatisticByName(String name);
    ... getAllEventStatistic();
    void printStatistic();
}
```
* incEvent(String name) - инкрементит число событий name;
* getEventStatisticByName(String name) - выдает rpm (request per minute) события name за последний час;
* getAllEventStatistic() - выдает rpm всех произошедших событий за прошедший час;
* printStatistic() - выводит в консоль rpm всех произошедших событий;

Реализацию `EventsStatistic` необходимо покрыть тестами, используя паттерн `Clock`, рассмотренный на лекции. Тесты не должны использовать sleep'ы и должны выполняться быстро.

## Указание:
* задание можно сдавать лично или через e-mail (в заголовке письма указать “[SD-TASK]”, в письме указать ссылку на ваш код на github.com)
