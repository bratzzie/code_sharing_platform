package com.stud.codesharing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CodeService {
    @Autowired
    private CodeSnippetRepository repository;

    Code deleteCodeByIdAndViewsRestrictedAndViewsIsLessThanEqual(String id) {
        Optional<Code> codeSnippet = repository.findById(id);
        if (codeSnippet.isPresent()) {
            if (codeSnippet.get().getViews() <= 0) {
                repository.deleteById(id);
                return codeSnippet.get();
            }
            return null;
        }
        return null;
    }

    Code deleteCodeByIdAndTimeRestrictedAndTimeIsLessThanEqual(String id) {
        Optional<Code> codeSnippet = repository.findById(id);
        if (codeSnippet.isPresent()) {
            if (codeSnippet.get().getTime() <= 0) {
                repository.deleteById(id);
                return codeSnippet.get();
            }
            return null;
        }
        return null;
    }

    Code deleteCodeByIdAndViewsRestrictedAndViewsIsLessThanEqualAndTimeRestrictedAndTimeLessThanEqual(String id) {
        Optional<Code> codeSnippet = repository.findById(id);
        if (codeSnippet.isPresent()) {
            if (codeSnippet.get().getTime() <= 0 && codeSnippet.get().getViews() <= 0) {
                repository.deleteById(id);
                return codeSnippet.get();
            }
            return null;
        }
        return null;
    }
}
