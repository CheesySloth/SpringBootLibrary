package com.library.core.mappers;

import com.library.core.domain.dto.BookDto;
import com.library.core.domain.entities.Book;

public interface BookMapper {
    Book fromDto(BookDto bookDto);

    BookDto toDto(Book book);
}
