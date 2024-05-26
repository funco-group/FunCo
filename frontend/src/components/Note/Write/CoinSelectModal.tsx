import BrandButtonComponent from '@/components/Common/Button/BrandButtonComponent'
import {
  SettleModalBackgroundContainer,
  SettleModalContainer,
  SettleModalTitleDiv,
} from '@/components/TradeHistory/Follow/Following/SettleModal.styled'
import { codeListState, codeNameMapState } from '@/recoils/crypto'
import searchByChosung from '@/utils/searchByChosung'
import { ChangeEvent, Dispatch, SetStateAction, useState } from 'react'
import { useRecoilValue } from 'recoil'

interface CoinSelectModalProps {
  coin: string
  setCoin: Dispatch<SetStateAction<string>>
  setOpenModal: Dispatch<SetStateAction<boolean>>
}

function CoinSelectModal({
  coin,
  setCoin,
  setOpenModal,
}: CoinSelectModalProps) {
  const AllCoinList = useRecoilValue(codeListState)
  const coinMap = useRecoilValue(codeNameMapState)

  const MappingCoinList = AllCoinList.map((ticker) => ({
    ticker,
    name: coinMap.get(ticker) || '',
  }))

  const [isInputFocus, setIsInputFocus] = useState(false)
  const [searchText, setSearchText] = useState('')

  const handleSearchChange = (event: ChangeEvent<HTMLInputElement>) => {
    setSearchText(event.target.value)
  }

  const handleInputFocus = () => {
    setIsInputFocus((prev) => !prev)
  }

  const handleCheckBtn = () => {
    setOpenModal(false)
  }

  const handleCoinBtn = (coinName: string) => {
    setCoin(coinName)
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
                className="ml-1 h-7 border-none bg-transparent text-brandDarkGray outline-none placeholder:text-brandDarkGray focus:text-brandColor focus:placeholder:text-brandColor"
              />
            </div>
            <div className="mt-3 min-h-[38px]">
              {coin.length > 0 ? (
                <button
                  key={coin}
                  type="button"
                  className="border-1 my-1 mr-1 rounded border-solid border-brandColor bg-brandWhite p-1 text-brandColor"
                  onClick={() => handleCoinBtn(coin)}
                >
                  {coinMap.get(coin)}
                </button>
              ) : null}
            </div>
          </div>

          <div className="no-scrollbar mb-3 h-80 overflow-auto border-b border-l-0 border-r-0 border-t-0 border-solid border-borderGray py-2">
            {MappingCoinList.filter(
              (MappingCoin) =>
                searchByChosung(searchText, MappingCoin.name) &&
                MappingCoin.ticker !== coin,
            ).map((MappingCoin) => (
              <button
                key={MappingCoin.name}
                type="button"
                className="border-1 my-1 mr-1 rounded border-solid border-deactivatedGray bg-brandWhite p-1 text-brandDarkGray"
                onClick={() => handleCoinBtn(MappingCoin.ticker)}
              >
                {MappingCoin.name}
              </button>
            ))}
          </div>
        </div>

        <div className="flex justify-end">
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

export default CoinSelectModal
