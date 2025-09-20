INSERT INTO user (id, name, email, password) VALUES
(1, 'a', 'a@a', '{bcrypt}$2a$10$RvQ3SdLJx0QIlWPC.k.e7ecDSw2EcemwaL9JlDXn4h5D/o8NUo7yS'),
(2, 'b', 'b@b', '{bcrypt}$2a$10$1JEgbOoQeP2vZTa0LIUSzu52szbjPusIcDtIuXvc1aLUpHCsmLUmi');

INSERT INTO label (id, user_id, name) VALUES
(1, 1, '家事'),
(2, 1, '健康');

INSERT INTO project (id, user_id, name) VALUES
(1, 1, 'プライベート'),
(2, 1, '仕事');

INSERT INTO todo (id, user_id, project_id, is_completed, name, `desc`, priority, due_date, due_time) VALUES
(1, 1, 1, TRUE, 'todo1', 'desc1', 1, CURDATE(), CURTIME()),
(2, 2, 1, FALSE, 'todo2', 'desc2', 2, CURDATE(), CURTIME()),
(3, 1, 2, TRUE, 'todo3', 'desc3', 3, CURDATE(), CURTIME()),
(4, 2, 2, FALSE, 'todo4', 'desc4', 4, CURDATE(), CURTIME());

INSERT INTO todo_label (todo_id, label_id) VALUES
(1, 1),
(2, 1),
(2, 2),
(3, 1),
(4, 1),
(4, 2);
