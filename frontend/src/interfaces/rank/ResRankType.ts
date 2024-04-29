import { RankType } from "./RankType";

export interface ResRankType {
  content: RankType[];
  pageable: {
    pageNumber: number;
    pageSize: number;
    sort: SortType;
    offset: number;
    paged: boolean;
    unpaged: boolean;
  };
  totalElements: number;
  totalPages: number;
  last: boolean;
  size: number;
  number: number;
  sort: SortType;
  numberOfElements: number;
  first: boolean;
  empty: false;
}

interface SortType {
  empty: boolean;
  sorted: boolean;
  unsorted: boolean;
}
