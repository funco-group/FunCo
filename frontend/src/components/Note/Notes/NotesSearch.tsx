/* eslint-disable jsx-a11y/control-has-associated-label */
import { ChangeEvent, Dispatch, SetStateAction, useState } from 'react'
import SearchSVG from '@/../public/icon/search-icon-copy.svg'
import palette from '@/lib/palette'

interface NotesSearchProps {
  setSearch: Dispatch<SetStateAction<string>>
  setKeyword: Dispatch<SetStateAction<string>>
}

function NotesSearch({ setSearch, setKeyword }: NotesSearchProps) {
  const [searchCategory, setSearchCategory] = useState('TITLE') // 검색 카테고리 상태
  const [searchText, setSearchText] = useState('') // 검색 텍스트 상태
  const [isInputFocus, setIsInputFocus] = useState(false)

  const handleCategoryChange = (event: ChangeEvent<HTMLSelectElement>) => {
    setSearchCategory(event.target.value)
  }

  const handleSearchChange = (event: ChangeEvent<HTMLInputElement>) => {
    setSearchText(event.target.value)
  }

  const handleInputFocus = () => {
    setIsInputFocus((prev) => !prev)
  }

  const handleSearch = () => {
    console.log(`검색: ${searchCategory}, 키워드: ${searchText}`)
    // 검색 로직 추가 위치
    setSearch(searchCategory)
    setKeyword(searchText)
  }

  const focusClasses =
    'focus:border-brandColor focus:border-2 focus:text-brandColor'

  const inptFocusClasses = isInputFocus
    ? 'border-brandColor border-2 text-brandColor'
    : 'border-deactivatedGray border-2'

  return (
    <div className="flex h-9">
      <div className="flex items-center">
        <select
          onChange={handleCategoryChange}
          value={searchCategory}
          className={`h-9 rounded border-2 border-solid border-deactivatedGray text-brandDarkGray outline-none ${focusClasses}`}
        >
          <option value="TITLE">제목</option>
          <option value="CONTENT">내용</option>
          <option value="WRITER">작성자</option>
        </select>
      </div>
      <div
        className={` ml-1 flex items-center rounded border-solid bg-brandWhite ${inptFocusClasses} p-1`}
      >
        <input
          type="text"
          value={searchText}
          onChange={handleSearchChange}
          placeholder="검색어 입력"
          onFocus={handleInputFocus}
          onBlur={handleInputFocus}
          className="h-5 border-none bg-transparent text-brandDarkGray outline-none placeholder:text-brandDarkGray focus:text-brandColor focus:placeholder:text-brandColor"
        />
        <button
          type="button"
          onClick={handleSearch}
          className="h-7 w-7 border-none bg-transparent"
        >
          <SearchSVG fill={palette.brandColor} />
        </button>
      </div>
    </div>
  )
}

export default NotesSearch
