import styled from "styled-components";
import palette from "@/lib/palette";

export const CoinSearchContainer = styled.div`
  padding: 0.7rem 0.5rem;
  display: flex;
  border-bottom: 1px solid ${palette.borderGray};

  img {
    vertical-align: middle;
  }

  input {
    border: none;
    margin-left: 0.2rem;
    font-size: 0.9rem;
    width: 100%;
    outline: none;
    font-family: "NanumSquare";

    &::placeholder {
      color: ${palette.borderGray};
    }
  }
`;

export const CoinSearch = styled.div``;
