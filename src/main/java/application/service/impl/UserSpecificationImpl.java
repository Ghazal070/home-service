package application.service.impl;

import application.dto.SearchDto;
import application.entity.Duty_;
import application.entity.users.*;
import application.exception.ValidationException;
import application.repository.CustomerRepository;
import application.repository.ExpertRepository;
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
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserSpecificationImpl<T extends Users> implements UserSpecification<T> {

    private final UserRepository<T> userRepository;
    private final CustomerRepository customerRepository;
    private final ExpertRepository expertRepository;

    @Override public void validate(SearchDto searchDto) {
        if (searchDto.getUserRole() != null && !(searchDto.getUserRole().equals("Customer") || searchDto.getUserRole().equals("Expert"))) {
            throw new ValidationException("UserType must be Customer or Expert");
        }
        if (searchDto.getUserRole() != null && searchDto.getUserRole().equals("Customer")) {
            if (searchDto.getDutyId() != null || searchDto.getMinScore() != null || searchDto.getMaxScore() != null) {
                throw new ValidationException("Customer don't have score or duty title");
            }
        }
    }

    @Override public List findAllBySearchDto(SearchDto searchDto) {
        validate(searchDto);
        if (searchDto.getUserRole() != null) {
            return handleSpecificUserRole(searchDto);
        } else {
            return handleGenericUserSearch(searchDto, userRepository);
        }
    }

    private List handleSpecificUserRole(SearchDto searchDto) {
        if (searchDto.getUserRole().equals("Customer")) {
            return customerRepository.findAll((root, query, cb) -> {
                List<Predicate> predicates = new ArrayList<>();
                populateCommonPredicates(predicates, root, cb, searchDto);
                return cb.and(predicates.toArray(new Predicate[0]));
            });
        } else if (searchDto.getUserRole().equals("Expert")) {
            return expertRepository.findAll((root, query, cb) -> {
                List<Predicate> predicates = new ArrayList<>();
                populateCommonPredicates(predicates, root, cb, searchDto);
                fillDutyId(predicates, root, cb, searchDto.getDutyId());
                fillMinScore(predicates, root, cb, searchDto.getMinScore(), searchDto.getMaxScore());
                return cb.and(predicates.toArray(new Predicate[0]));
            });
        } else {
            throw new ValidationException("User role can only be Customer or Expert or null");
        }
    }
//done edit duplicate code
    private List handleGenericUserSearch(SearchDto searchDto, UserRepository<T> userRepository) {
        return userRepository.findAll(
                (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            populateCommonPredicates(predicates, root, cb, searchDto);
            fillDutyId(predicates, root, cb, searchDto.getDutyId());
            fillMinScore(predicates, root, cb, searchDto.getMinScore(), searchDto.getMaxScore());
            return cb.and(predicates.toArray(new Predicate[0]));
        });
    }

    private void populateCommonPredicates(List<Predicate> predicates, Root root, CriteriaBuilder cb, SearchDto searchDto) {
        fillFirstName(predicates, root, cb, searchDto.getFirstName());
        fillLastName(predicates, root, cb, searchDto.getLastName());
        fillEmail(predicates, root, cb, searchDto.getEmail());
    }

    private void fillFirstName(List<Predicate> predicates, Root root, CriteriaBuilder cb, String firstName) {
        if (StringUtils.isNotBlank(firstName)) {
            predicates.add(cb.like(root.get(Users_.FIRST_NAME), "%" + firstName + "%"));
        }
    }

    private void fillLastName(List<Predicate> predicates, Root root, CriteriaBuilder cb, String lastName) {
        if (StringUtils.isNotBlank(lastName)) {
            predicates.add(cb.like(root.get(Users_.LAST_NAME), "%" + lastName + "%"));
        }
    }

    private void fillEmail(List<Predicate> predicates, Root root, CriteriaBuilder cb, String email) {
        if (StringUtils.isNotBlank(email)) {
            predicates.add(cb.like(root.get(Users_.PROFILE).get(Profile_.EMAIL), "%" + email + "%"));
        }
    }

    private void fillDutyId(List<Predicate> predicates, Root root, CriteriaBuilder cb, Set<Integer> dutyId) {
        List<Predicate> orPredicates=new ArrayList<>();
        if (dutyId!=null && !dutyId.isEmpty()) {
            for (int id:dutyId) {
                orPredicates.add(
                        cb.equal(
                                root.join(Expert_.DUTIES).get(Duty_.ID), id));
            }
            predicates.add(cb.or(orPredicates.toArray(new Predicate[0])));
        }
    }

    private void fillMinScore(List<Predicate> predicates, Root root, CriteriaBuilder cb, Integer minScore, Integer maxScore) {
        if (minScore != null && maxScore != null) {
            predicates.add(cb.between(root.get(Expert_.SCORE), minScore, maxScore));
        }
        else if (minScore != null) {
            predicates.add(cb.between(root.get(Expert_.SCORE), minScore,Integer.MAX_VALUE));
        }
        else if (maxScore != null) {
            predicates.add(cb.between(root.get(Expert_.SCORE), Integer.MIN_VALUE,maxScore));
        }
    }
//done handle min Score , infighting
}