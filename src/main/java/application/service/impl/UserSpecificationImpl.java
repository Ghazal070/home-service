package application.service.impl;

import application.dto.SearchDto;
import application.entity.Duty_;
import application.entity.users.Expert_;
import application.entity.users.Profile_;
import application.entity.users.Users;
import application.entity.users.Users_;
import application.exception.ValidationException;
import application.repository.UserRepository;
import application.service.UserSpecification;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserSpecificationImpl implements UserSpecification {

    private final UserRepository userRepository;

    @Override
    public void validate(SearchDto searchDto) {
        if (searchDto.getUserRole() != null && !(searchDto.getUserRole().equals("Customer") || searchDto.getUserRole().equals("Expert"))) {
            throw new ValidationException("UserType must be Customer or Expert");
        }
        if (searchDto.getUserRole() != null && searchDto.getUserRole().equals("Customer")) {
            if (searchDto.getDutyTitle() != null || searchDto.getMinScore() != null || searchDto.getMaxScore() != null) {
                throw new ValidationException("Customer dont have score or duty title");
            }
        }
    }

    @Override
    public List<Users> findAllBySearchDto(SearchDto searchDto) {
        validate(searchDto);
        return userRepository.findAll(
                (root, query, cb) -> {
                    List<Predicate> predicates = new ArrayList<>();
                    fillUserRole(predicates, root, cb, searchDto.getUserRole());
                    fillFirstName(predicates, root, cb, searchDto.getFirstName());
                    fillLastName(predicates, root, cb, searchDto.getLastName());
                    fillEmail(predicates, root, cb, searchDto.getEmail());
                    fillDutyTitle(predicates, root, cb, searchDto.getDutyTitle());
                    fillMinScore(predicates, root, cb, searchDto.getMinScore(), searchDto.getMaxScore());

                    return cb.and(predicates.toArray(new Predicate[0]));

                }
        );
    }

    private void fillUserRole(List<Predicate> predicates,
                              Root<Users> root, CriteriaBuilder cb, String userRole) {
        if (StringUtils.isNotBlank(userRole)) {
            predicates.add(
                    cb.equal(
                            root.get("dtype"),
                            userRole
                    )
            );
        }
    }

    private void fillFirstName(List<Predicate> predicates, Root<Users> root
            , CriteriaBuilder cb, String firstName) {
        if (StringUtils.isNotBlank(firstName)) {
            predicates.add(
                    cb.like(
                            root.get(Users_.FIRST_NAME),
                            "%" + firstName + "%"
                    )
            );
        }
    }

    private void fillLastName(List<Predicate> predicates, Root<Users> root
            , CriteriaBuilder cb, String lastName) {
        if (StringUtils.isNotBlank(lastName)) {
            predicates.add(
                    cb.like(
                            root.get(Users_.LAST_NAME),
                            "%" + lastName + "%"
                    )
            );
        }
    }

    private void fillEmail(List<Predicate> predicates, Root<Users> root
            , CriteriaBuilder cb, String email) {
        if (StringUtils.isNotBlank(email)) {
            predicates.add(
                    cb.like(
                            root.get(Users_.PROFILE).get(Profile_.EMAIL),
                            "%" + email + "%"
                    )
            );
        }
    }

    private void fillDutyTitle(List<Predicate> predicates, Root<Users> root
            , CriteriaBuilder cb, String dutyTitle) {
        if (StringUtils.isNotBlank(dutyTitle)) {
            predicates.add(
                    cb.like(
                            root.join(Expert_.DUTIES).get(Duty_.TITLE),
                            "%" + dutyTitle + "%"
                    )
            );
        }
    }

    private void fillMinScore(List<Predicate> predicates, Root<Users> root, CriteriaBuilder cb, Integer minScore, Integer maxScore) {
        if (minScore != null && maxScore != null) {
            predicates.add(
                    cb.between(
                            root.get(Expert_.SCORE), minScore, maxScore
                    )
            );
        }
    }
}
