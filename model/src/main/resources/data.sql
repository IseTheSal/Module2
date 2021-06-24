INSERT INTO public.tags (id, name)
VALUES (1, 'snow');
INSERT INTO public.tags (id, name)
VALUES (2, 'desert');
INSERT INTO public.tags (id, name)
VALUES (3, 'beach');
INSERT INTO public.tags (id, name)
VALUES (4, 'sky');
INSERT INTO public.tags (id, name)
VALUES (5, 'flight');

INSERT INTO public.gift_certificates (id, name, description, price, duration, create_date, last_update_date)
VALUES (1, 'Квадроцикл', 'Увлекательно весело и здорово', 20.04, 3, '2021-06-14 00:05:01.717000',
        '2021-06-14 00:05:01.717000');
INSERT INTO public.gift_certificates (id, name, description, price, duration, create_date, last_update_date)
VALUES (2, 'Мотокицл', 'Скучно невесело и вредно', 20.99, 6, '2021-06-14 00:05:38.389000',
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

INSERT INTO public.users(id, login)
VALUES (1, 'isethesal');
INSERT INTO public.users(id, login)
VALUES (2, 'ileathehunter');