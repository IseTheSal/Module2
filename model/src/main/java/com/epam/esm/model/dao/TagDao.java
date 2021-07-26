//package com.epam.esm.model.dao;
//
//import com.epam.esm.model.entity.Tag;
//
//import java.util.Optional;
//
///**
// * Interface extends {@link Tag} database functionality
// *
// * @author Illia Aheyeu
// */
//public interface TagDao extends CommonDao<Tag> {
//
//    /**
//     * Search {@link Tag} by name
//     *
//     * @param name {@link Tag}  name
//     * @return <code>Optional</code> {@link Tag}
//     */
//    Optional<Tag> findByName(String name);
//
//    /**
//     * Create new {@link Tag Tag} in database.
//     *
//     * @param tag Any Object that implements {@link Tag} interface
//     * @return That created tag
//     */
//    Tag create(Tag tag);
//
//    /**
//     * Delete {@link Tag } from database by provided <code>id</code>.
//     *
//     * @param id <code>id</code> of object that implements {@link Tag}
//     * @return <code>True</code> if {@link Tag } was deleted, otherwise <code>false</code>
//     */
//    boolean delete(long id);
//
//    /**
//     * Find the most widely used tag of a user with the highest cost of all orders.
//     *
//     * @return <code>Optional</code> of {@link Tag}
//     */
//    Optional<Tag> findMostWidelyUsedTag();
//}
