## Android Login

*Поставленная задача: написать прототип приложения с использованием шаблона проектирования MVP (Model—View—Presenter) и принципов чистой архитектуры.*

> В настоящий момент приложение не завершено и не соответствует ТЗ в полной мере.  
> Я экспериментирую с ним в свободое время.

### Описание интерфейса и функциональности приложения.

Приложение состоит из двух экранов.

#### Первый экран содержит следующие элементы:

* приветствие,
* строка ввода логина,
* строка ввода пароля,
* кнопка "Вход".

Логин должен быть длиной не менее 4х символов, не содержать точек и пробелов.  
Пароль должен быть длиной не менее 6 символов, не содержать подряд одинаковых символов и содержать минимум 3 числа и 3 буквы.  
В процессе ввода должно быть отображено предупреждение, если логин и пароль не соответствуют условиям.  
Переход на второй экран должен осуществляться при нажатии кнопки 'вход' и соответствующим условиям логину и паролю.  
Введённые логин и пароль должны сохраняться в недоступном для других приложений и внешних пользователей месте в зашифрованном виде.  
Срок хранения логина и пароля — 5 минут, затем они должны быть удалены. Если в этот момент приложение было открыто, то оно должно перейти на первый экран.  
При повторном запуске приложения, если есть сохранённые логин и пароль, приложение должно сразу перейти на второй экран.

#### Второй экран содержит следующие элементы:

* приветствие, содержащее логин пользователя,
* кнопка "Выход",
* кнопка "Забыть пользователя".

Приложение должно быть закрыто при нажатии кнопки "Выход".
Сохраненные логин и пароль пользователя должны быть удалены при нажатии кнопки "Забыть пользователя", после чего приложение должно перейти на первый экран.
