declareTasks {
    addTask(id: 1, name: 'Task_1_1_1', maxScores: 10, softDeadline: '2025-09-08', hardDeadline: '2025-09-13')
    addTask(id: 2, name: 'Task_1_1_2', maxScores: 10, softDeadline: '2025-09-20', hardDeadline: '2025-09-27')
    addTask(id: 3, name: 'Task_2_1_1', maxScores: 10, softDeadline: '2026-02-14', hardDeadline: '2026-02-21')
    addTask(id: 4, name: 'Task_2_2_1', maxScores: 10, softDeadline: '2026-03-07', hardDeadline: '2026-03-14')
}

declareCheckpoints {
    addCheckpoint(name: 'Первый семестр', date: '2026-01-01')
    addCheckpoint(name: 'Второй семестр', date: '2026-06-25')
}

declareGroups {
    addGroup('24213') {
        addStudent(nameGit: 'IlyaStub', fio: 'Стубарев Илья Денисович', repoLink: 'https://github.com/IlyaStub/OOP')
//        addStudent(nameGit: 'PytoByte', fio: 'Маркидонов Владимир Владимирович', repoLink: 'https://github.com/PytoByte/OOP')
    }
//    addGroup('24214') {
//        addStudent(nameGit: 'IlyaStub2', fio: 'Стубарев Илья Денисович 2', repoLink: 'https://github.com/IlyaStub/OOP')
//    }
}

command('test') {
    check(group: '24213', taskId: 1)
    check(group: '24213', taskId: 2)
    check(group: '24213', taskId: 3)
    check(group: '24213', taskId: 4)
//    check(group: '24214', taskId: 1)
//    check(group: '24214', taskId: 2)
//    check(group: '24214', taskId: 3)
//    check(group: '24214', taskId: 4)
    bonus(studentGit: 'IlyaStub', taskId: 1, points: 2)
}