package Service;

import Entities.User;
import Models.UserDTO;
import jakarta.persistence.EntityManager;
import Entities.Cat;
import Models.CatDTO;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@Service
public class CatMapper {

    private final ColorMapper colorMapper;
    private final EntityManager manager;

    public CatMapper(ColorMapper colorMapper, EntityManager manager) {
        this.colorMapper = colorMapper;
        this.manager = manager;
    }

    public CatDTO toCatDTO(Cat cat) {
        CatDTO catDataObject = new CatDTO();
        catDataObject.setId(cat.getId());
        catDataObject.setName(cat.getName());
        catDataObject.setColor(colorMapper.getStringFromColor(cat.getColor()));

        if (cat.getOwnerId() != null) {
            catDataObject.setUserId(cat.getOwnerId());
        }

        catDataObject.setMyFriendsIds(cat.getFriendCats().stream().map(Cat::getId).collect(Collectors.toSet()));
        return catDataObject;
    }

    public Cat toCat(CatDTO catDto) {
        Cat cat = new Cat();
        cat.setName(catDto.getName());
        cat.setId(catDto.getId());

        cat.setOwnerId(catDto.getUserId());
        cat.setColor(colorMapper.getColorFromString(catDto.getColor()));

        Set<Long> friendIds = catDto.getMyFriendsIds() != null ?
                catDto.getMyFriendsIds() : Collections.emptySet();

        cat.setFriendCats(friendIds.stream().map(x -> manager.find(Cat.class, x)).collect(Collectors.toSet()));
        return cat;
    }

    public Iterable<CatDTO> toCatDTOs(Iterable<Cat> cats) {
        return StreamSupport.stream(cats.spliterator(), false)
                .map(this::toCatDTO)
                .collect(Collectors.toList());
    }

    public Iterable<Cat> toCats(Iterable<CatDTO> catDTOs) {
        Iterable<Cat> cats = StreamSupport.stream(catDTOs.spliterator(), false)
                .map(this::toCat)
                .collect(Collectors.toList());
        return cats;
    }
}
