import { memo } from "react";
import { CoinSearchContainer } from "./CoinSearch.styled";
import SearchIcon from "@/assets/icon/search-icon.svg";

interface CoinSearchProps {
  setSearch: React.Dispatch<React.SetStateAction<string>>;
}

const CoinSearch = memo(({ setSearch }: CoinSearchProps) => {
  return (
    <CoinSearchContainer>
      <img src={SearchIcon} alt="search-icon" />
      <input
        type="text"
        placeholder="코인명/심볼검색"
        onChange={(e) => {
          setSearch(e.target.value);
        }}
      />
    </CoinSearchContainer>
  );
});

export default CoinSearch;
