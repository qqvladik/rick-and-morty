# rick-and-morty

Разработал: Манкевич Владислав Петрович

Курсовой проект Рик и Морти.
Техническое задание прикреплено отдельным файлом

Реализовано:
- SingleActivity  (ветка lesson05fragments)
- MVVM            (ветка lesson05fragments)
- Coroutines      (ветка lesson08retrofit, lesson09coroutines, ветка lesson12coroutines)
- Retrofit        (ветка lesson08retrofit)
- Room            (ветка lesson09room)
- Picasso         (ветка lesson06recycler)
- Paging 3        (ветка lesson09room)
- Pull to refresh (ветка pull_refresh_feature)
- Splash screen   (ветка splash_screen_feature)
- Tests Junit 5   (ветка tests)

Не реализовано:
- Dagger 2

Баги и нереализованные фичи из ТЗ:
- Баг: При отсутствии результатов по поиску уведомление Toast 
  "No result" отображается по нескольку раз с каждым добавленным
  символом.
  Возможное решение: не использовать Toast, а показывать TextView.
  
- Нереализованная фича: Не отображается Progress Bar при загрузке
  первой страницы ReyclerView. 
  Возможное решение: Использовать State и отображать Progress Bar 
  в зависимости от  значений State: Loading, Error, NotLoading.
  
Дополнительные фичи:
- Приложение имеет светлую и темную тему с разным стилем оформления.
- Добавлен ImageView в центре сверху экрана списка, отображающий
  каким способом загружены данные: через сеть либо из базы данных.
- При переходе на экран детализации, над списком отображается текст
  о том, что могут быть отображены не все элементы, если список 
  загружается из базы данных.
