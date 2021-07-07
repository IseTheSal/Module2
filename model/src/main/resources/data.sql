INSERT INTO public.tags (id, name, create_date, last_update_date)
VALUES (1, 'snow', now(), now());
INSERT INTO public.tags (id, name, create_date, last_update_date)
VALUES (2, 'desert', now(), now());
INSERT INTO public.tags (id, name, create_date, last_update_date)
VALUES (3, 'beach', now(), now());
INSERT INTO public.tags (id, name, create_date, last_update_date)
VALUES (4, 'sky', now(), now());
INSERT INTO public.tags (id, name, create_date, last_update_date)
VALUES (5, 'flight', now(), now());

INSERT INTO public.gift_certificates (id, name, description, price, duration, create_date, last_update_date)
VALUES (1, 'Квадроцикл', 'Увлекательно весело и здорово', 20.04, 3, '2021-06-14 00:05:01.717000',
        '2021-06-14 00:05:01.717000');
INSERT INTO public.gift_certificates (id, name, description, price, duration, create_date, last_update_date)
VALUES (2, 'Мотоцикл', 'Скучно невесело и вредно', 20.99, 6, '2021-06-14 00:05:38.389000',
        '2021-06-14 00:05:38.389000');
INSERT INTO public.gift_certificates (id, name, description, price, duration, create_date, last_update_date)
VALUES (3, 'Вертолет', 'Экстримально и страшно', 120.99, 30, '2021-06-14 00:06:20.111000',
        '2021-06-14 00:06:20.111000');

INSERT INTO public.certificate_tag (certificate_id, tag_id)
VALUES (1, 1);
INSERT INTO public.certificate_tag (certificate_id, tag_id)
VALUES (1, 2);
INSERT INTO public.certificate_tag (certificate_id, tag_id)
VALUES (1, 3);
INSERT INTO public.certificate_tag (certificate_id, tag_id)
VALUES (2, 2);
INSERT INTO public.certificate_tag (certificate_id, tag_id)
VALUES (2, 3);

INSERT INTO public.certificate_tag (certificate_id, tag_id)
VALUES (3, 4);
INSERT INTO public.certificate_tag (certificate_id, tag_id)
VALUES (3, 5);

INSERT INTO public.users(id, login, create_date, last_update_date)
VALUES (1, 'isethesal', now(), now());
INSERT INTO public.users(id, login, create_date, last_update_date)
VALUES (2, 'ileathehunter', now(), now());

INSERT INTO public.orders (user_id, certificate_id, price, purchase_date, id, create_date, last_update_date)
VALUES (1, 1, 20.05, '2021-05-24 20:48:10.000000', 1, now(), now());
INSERT INTO public.orders (user_id, certificate_id, price, purchase_date, id, create_date, last_update_date)
VALUES (2, 1, 15.15, '2021-05-24 20:48:27.000000', 2, now(), now());
INSERT INTO public.orders (user_id, certificate_id, price, purchase_date, id, create_date, last_update_date)
VALUES (1, 2, 30.5, '2021-05-24 20:48:39.000000', 3, now(), now());
INSERT INTO public.orders (user_id, certificate_id, price, purchase_date, id, create_date, last_update_date)
VALUES (1, 1, 23.34, '2021-05-24 21:10:38.000000', 4, now(), now());
INSERT INTO public.orders (user_id, certificate_id, price, purchase_date, id, create_date, last_update_date)
VALUES (2, 2, 45, '2021-05-24 21:10:56.000000', 6, now(), now());
INSERT INTO public.orders (user_id, certificate_id, price, purchase_date, id, create_date, last_update_date)
VALUES (2, 2, 574.56, '2021-05-24 21:11:04.000000', 7, now(), now());
INSERT INTO public.orders (user_id, certificate_id, price, purchase_date, id, create_date, last_update_date)
VALUES (2, 1, 574.56, '2021-05-24 21:10:47.000000', 5, now(), now());
INSERT INTO public.orders (user_id, certificate_id, price, purchase_date, id, create_date, last_update_date)
VALUES (2, 1, 10000, '2021-05-24 21:16:27.000000', 8, now(), now());
INSERT INTO public.orders (user_id, certificate_id, price, purchase_date, id, create_date, last_update_date)
VALUES (1, 1, 200000, '2021-05-24 21:16:45.000000', 9, now(), now());
INSERT INTO public.orders (user_id, certificate_id, price, purchase_date, id, create_date, last_update_date)
VALUES (1, 1, 200000, '2021-05-24 21:18:50.000000', 10, now(), now());

INSERT INTO public.tags (id, name, create_date, last_update_date)
VALUES (6, 'работает', now(), now());
INSERT INTO public.gift_certificates (id, name, description, price, duration, create_date, last_update_date)
VALUES (4, 'ТЕСТОВЫЙ', 'Экстримально и страшно', 12000.99, 30, '2021-05-14 00:06:20.111000',
        '2021-05-14 00:06:20.111000');
INSERT INTO public.gift_certificates (id, name, description, price, duration, create_date, last_update_date)
VALUES (5, 'ТЕСТОВЫЙ2', 'Экстримально и страшно', 120.99, 30, '2021-05-14 00:06:20.111000',
        '2021-05-14 00:06:20.111000');

INSERT INTO public.certificate_tag (certificate_id, tag_id)
VALUES (4, 6);
INSERT INTO public.certificate_tag (certificate_id, tag_id)
VALUES (5, 6);

INSERT INTO public.orders (user_id, certificate_id, price, purchase_date, id, create_date, last_update_date)
VALUES (1, 4, 500.05, '2021-05-24 20:48:10.000000', 11, now(), now());
INSERT INTO public.orders (user_id, certificate_id, price, purchase_date, id, create_date, last_update_date)
VALUES (1, 5, 500.05, '2021-05-24 20:49:10.000000', 12, now(), now());
INSERT INTO public.orders (user_id, certificate_id, price, purchase_date, id, create_date, last_update_date)
VALUES (1, 5, 5400.05, '2021-05-24 20:49:10.000000', 13, now(), now());