declareTasks {
    addTask(id: 1, name: 'Task_2_1_1', maxScores: 10, softDeadline: '2024-03-01', hardDeadline: '2024-03-07')
    addTask(id: 2, name: 'Task_2_3_1', maxScores: 10, softDeadline: '2024-03-15', hardDeadline: '2024-03-22')
}

declareCheckpoints {
    addCheckpoint(name: 'Сессия 1', date: '2024-03-25')
}

declareGroups {
    addGroup('24213') {
        addStudent(nameGit: 'Student1', fio: 'Студент №1', repoLink: 'https://github.com/IlyaStub/OOP')
        //addStudent(nameGit: 'Student2', fio: 'Студент №2', repoLink: 'https://github.com/IlyaStub/OOP')
    }
}

command('test') {
    check(group: '24213', taskId: 1)
    //check(group: '24214', taskId: 2)

    bonus(studentGit: 'Student2', taskId: 2, points: 1)
}