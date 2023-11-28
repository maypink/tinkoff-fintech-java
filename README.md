# Tinkoff Fintech Course

## Опыт работы

- __Examis, репетитор по математике (09/2019-02/2020)__

    - Подготовка к экзаменам (ОГЭ и ЕГЭ)
    - Проверка домашних работ

- __Freelance, Machine Learning Developer (06/2021-09/2021)__
    
    - Работа с текстом и изображениями, предобработка данных
    - Выбор метрик для оценки результата

- __NSS Lab, Machine Learning Researcher (01/2022 - present)__

    - Разработка open-source фреймворков [GOLEM](https://github.com/aimclub/GOLEM) и [FEDOT](https://github.com/aimclub/FEDOT)
    - Работа с эволюционными алгоритмами на графах
    - Постановка экспериментов, анализ результатов

## Навыки

- __Programming Languages__ 
  - Python (PyTorch, Scikit-learn, NetworkX, etc),
  - Java (Spring)
  - C++
  
- __Must-have__ 
  - Jupyter
  - Git
  - Linux, Bash
  - Docker


## Курсовой проект

- Приложение по изучению языков
    - Автоматическое формирование сетов слов для изучения из тех, что пользователь уже добавил самостоятельно.
    - Каждое слово будет изучаться в контексте предложения (могут быть спаршены с `Reverso` и у `Яндекс переводчика` тоже хорошая база примеров). Идея с предложениями есть в приложении `Lingvist`, однако там фиксированный набор слов от создателей.
    - Изучение в формате карточек (например, как в `Quizlet`)
- Или что-то из предложенных тем ;)

## Домашнее задание #1

- `git init` 
  - Инициализирует репозиторий -- создает новый подкаталог `.git`, содержащий структуру репозитория.
  - Пример использования: разработчик находится в директории, где хочет создать git репозиторий (e.g. cd `C:/Users/main_user/new_project`) и вызывает `git init`

- `git clone`
  - С помощью git clone можно получить копию уже существующего репозитория.
  - Пример использования:  `git clone https://github.com/desired_project/desired_project` Эта команда создаёт каталог `desired_project`, инициализирует в нём подкаталог `.git`, скачивает все данные для этого репозитория и извлекает актуальную (последнюю) копию репозитория.
  Также можно клонировать репозиторий в каталог с указанным именем. Для этого нужно вызвать следующую команду: `git clone https://github.com/desired_project/desired_project my_name` 

- `git add`
  - Добавляет изменение из рабочего каталога в раздел проиндексированных файлов (staging area).
  - Пример использования: разработчик изменил файл `changed_file.java` и хочет внести его в раздел проиндексированных файлов. В таком случае ему нужна команда `git add changed_file.java`

- `git commit`
  - Берёт все данные, добавленные в индекс с помощью `git add`, и сохраняет их снимок во внутренней (локальной) базе данных, а затем сдвигает указатель текущей ветки на этот снимок.
  - Пример использования: разработчик сделал часть задачи и хочет закоммитить изменения. В таком случае, он добавляет все измененные файлы в индекс с помощью `git add` и далее делает коммит с помощью `git commit -m "add part of my task"`. 
  Параметр `-m` позволяет добавить комментарий к коммиту. Также можно не использовать `git add` для добавления внесенных изменений в индекс, а сразу сделать `git commit -a -m "add part of my task"`, так что Git автоматически проиндексирует каждый файл, который уже отслеживался на момент коммита.

- `git push`
  - Используется для установления связи с удалённым репозиторием, вычисления локальных изменений по сравнению с ним, и их передачи в этот удаленный репозиторий.
  - Пример использования: разработчик хочет опубликовать внесенные им изменения в удаленном репозитории, поэтому выполняет действия, указанные в примере для команды
  `git commit`, а далее делает `git push`.

- `git pull`
  - При применении этой команды Git забирает изменения из указанного удалённого репозитория и пытается слить их с текущей веткой.
  - Пример использования: разработчик вернулся из отпуска и хочет обновить свою ветку `master`, так как коллеги за время его отсутствия влили в нее свои изменения. В таком случае, разработчику нужно вызвать команду `git pull`.
