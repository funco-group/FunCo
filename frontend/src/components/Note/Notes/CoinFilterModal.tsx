import BrandButtonComponent from '@/components/Common/Button/BrandButtonComponent'
import {
  SettleModalBackgroundContainer,
  SettleModalContainer,
  SettleModalTitleDiv,
} from '@/components/TradeHistory/Follow/Following/SettleModal.styled'
import { codeListState, codeNameMapState } from '@/recoils/crypto'
import {
  ChangeEvent,
  Dispatch,
  SetStateAction,
  useEffect,
  useState,
} from 'react'
import { useRecoilValue } from 'recoil'
import searchByChosung from '@/utils/searchByChosung'

interface NoteCoinType {
  ticker: string
  name: string
  selected: boolean
}

interface CoinFilterModalProps {
  setCoinList: Dispatch<SetStateAction<string[]>>
  setOpenModal: Dispatch<SetStateAction<boolean>>
  coinList: string[]
}

function CoinFilterModal({
  setCoinList,
  setOpenModal,
  coinList,
}: CoinFilterModalProps) {
  const AllCoinList = useRecoilValue(codeListState)
  const coinMap = useRecoilValue(codeNameMapState)

  const initializeMappingCoinList = () =>
    AllCoinList.map((coin) => ({
      ticker: coin,
      name: coinMap.get(coin) || '',
      selected: coinList.includes(coin),
    }))

  const [MappingCoinList, setMappingCoinList] = useState<NoteCoinType[]>(
    initializeMappingCoinList,
  )
  const [isInputFocus, setIsInputFocus] = useState(false)
  const [searchText, setSearchText] = useState('')

  useEffect(() => {
    setMappingCoinList(initializeMappingCoinList)
  }, [AllCoinList, coinList, coinMap])

  const handleSearchChange = (event: ChangeEvent<HTMLInputElement>) => {
    setSearchText(event.target.value)
  }

  const handleInputFocus = () => {
    setIsInputFocus((prev) => !prev)
  }

  const handleCancelBtn = () => {
    setOpenModal(false)
  }

  const handleCheckBtn = () => {
    const selectedCoins = MappingCoinList.filter((coin) => coin.selected).map(
      (coin) => coin.ticker,
    )
    setCoinList(selectedCoins)
    setOpenModal(false)
  }

  const handleCoinBtn = (coinName: string) => {
    setMappingCoinList((prevList) =>
      prevList.map((coin) =>
        coin.name === coinName ? { ...coin, selected: !coin.selected } : coin,
      ),
    )
  }

  const inputFocusClasses = isInputFocus
    ? 'border-brandColor border-2 text-brandColor'
    : 'border-deactivatedGray border-2'

  return (
    <SettleModalBackgroundContainer>
      <SettleModalContainer width="50%">
        <SettleModalTitleDiv>가상 화폐 필터</SettleModalTitleDiv>
        <div>
          <div className="border-b border-l-0 border-r-0 border-t-0 border-solid border-borderGray py-3">
            <div
              className={`flex items-center rounded border-solid bg-brandWhite ${inputFocusClasses} w-fit`}
            >
              <input
                type="text"
                value={searchText}
                onChange={handleSearchChange}
                placeholder="코인 이름 검색"
                onFocus={handleInputFocus}
                onBlur={handleInputFocus}
                className="ml-1 h-5 border-none bg-transparent text-brandDarkGray outline-none placeholder:text-brandDarkGray focus:text-brandColor focus:placeholder:text-brandColor"
              />
            </div>
            <div className="mt-3 min-h-[38px]">
              {MappingCoinList.filter((coin) => coin.selected).map((coin) => (
                <button
                  key={coin.name}
                  type="button"
                  className="border-1 my-1 mr-1 rounded border-solid border-brandColor bg-brandWhite p-1 text-brandColor"
                  onClick={() => handleCoinBtn(coin.name)}
                >
                  {coin.name}
                </button>
              ))}
            </div>
          </div>

          <div className="no-scrollbar mb-3 h-80 overflow-auto border-b border-l-0 border-r-0 border-t-0 border-solid border-borderGray py-2">
            {MappingCoinList.filter(
              (coin) =>
                searchByChosung(searchText, coin.name) && !coin.selected,
            ).map((coin) => (
              <button
                key={coin.name}
                type="button"
                className="border-1 my-1 mr-1 rounded border-solid border-deactivatedGray bg-brandWhite p-1 text-brandDarkGray"
                onClick={() => handleCoinBtn(coin.name)}
              >
                {coin.name}
              </button>
            ))}
          </div>
        </div>

        <div className="flex justify-end gap-3">
          <BrandButtonComponent
            color={null}
            content="취소"
            cancel
            onClick={handleCancelBtn}
            disabled={false}
          />
          <BrandButtonComponent
            color={null}
            content="확인"
            cancel={false}
            onClick={handleCheckBtn}
            disabled={false}
          />
        </div>
      </SettleModalContainer>
    </SettleModalBackgroundContainer>
  )
}

export default CoinFilterModal
