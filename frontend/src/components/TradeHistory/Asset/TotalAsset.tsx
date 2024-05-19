import {
  GreenContainer,
  GreenDataDiv,
  GreenTitleDiv,
} from '@/styles/TradeHistoryStyled'
import { AssetType, TotalAssetType } from '@/interfaces/AssetType'
import AlertWithCancelModal from '@/components/Common/Modal/AlertWithCancelModal'
import { Dispatch, SetStateAction, useState } from 'react'
import { patchInitCash } from '@/apis/asset'
import { usePathname } from 'next/navigation'
import {
  AssetItemContainer,
  AssetItemDiv,
  TotalAssetContainer,
} from './TotalAsset.styled'

interface TotalAssetProps {
  totalAsset: TotalAssetType | undefined
  setAssets: Dispatch<SetStateAction<AssetType[]>>
}

function TotalAsset({ totalAsset, setAssets }: TotalAssetProps) {
  const [onAlertModal, setOnAlertModal] = useState(false)
  const pathName = usePathname()

  const handleInitCash = () => {
    setOnAlertModal(true)
  }
  return (
    <TotalAssetContainer>
      {onAlertModal && (
        <AlertWithCancelModal
          title="경고"
          content={
            <div>
              <div className="mb-2 text-lg text-brandRed">
                원 초기화 시 팔로우 기능이 강제 청산되며 모든 자산이
                초기화됩니다.
              </div>
              <div>그래도 초기화 하시겠습니까?</div>
            </div>
          }
          cancelAlert={() => {
            setOnAlertModal(false)
          }}
          confirmAlert={() => {
            patchInitCash(() => {
              setAssets([
                {
                  imgSrc: '/icon/cash-icon.png',
                  type: 'cash',
                  name: '현금',
                  volume: null,
                  averagePrice: null,
                  price: null,
                  evaluationAmount: 5000000,
                  evaluationProfit: null,
                },
                {
                  imgSrc: '/icon/follow-icon.png',
                  type: 'follow',
                  name: '팔로우',
                  volume: null,
                  averagePrice: null,
                  price: 0,
                  evaluationAmount: null,
                  evaluationProfit: null,
                },
              ])
              setOnAlertModal(false)
            })
          }}
        />
      )}
      <GreenContainer>
        <AssetItemContainer $top>
          <AssetItemDiv>
            <GreenTitleDiv>보유</GreenTitleDiv>
            <GreenDataDiv color="black">
              {totalAsset?.cash.toLocaleString('ko-KR')} <span>WON</span>
            </GreenDataDiv>
          </AssetItemDiv>
          <AssetItemDiv>
            <GreenTitleDiv>총 보유자산</GreenTitleDiv>
            <GreenDataDiv color="black">
              {totalAsset?.asset.toLocaleString('ko-KR')} <span>WON</span>
            </GreenDataDiv>
          </AssetItemDiv>
        </AssetItemContainer>
        <AssetItemContainer $top={false}>
          <AssetItemDiv>
            <GreenTitleDiv>총 매수금액</GreenTitleDiv>
            <GreenDataDiv color="black">
              {totalAsset?.price.toLocaleString('ko-KR')} <span>WON</span>
            </GreenDataDiv>
          </AssetItemDiv>
          <AssetItemDiv>
            <GreenTitleDiv>총 평가손익</GreenTitleDiv>
            <GreenDataDiv
              color={
                totalAsset?.returnResult.toString().startsWith('-')
                  ? 'blue'
                  : 'red'
              }
            >
              {totalAsset?.returnResult.toLocaleString('ko-KR')}{' '}
              <span>WON</span>
            </GreenDataDiv>
          </AssetItemDiv>
        </AssetItemContainer>
        <AssetItemContainer $top={false}>
          <AssetItemDiv>
            <GreenTitleDiv>총 평가금액</GreenTitleDiv>
            <GreenDataDiv color="black">
              {totalAsset?.evaluationAmount.toLocaleString('ko-KR')}{' '}
              <span>WON</span>
            </GreenDataDiv>
          </AssetItemDiv>
          <AssetItemDiv>
            <GreenTitleDiv>총 평가수익률</GreenTitleDiv>
            <GreenDataDiv
              color={
                totalAsset?.evaluationProfit.toString().startsWith('-')
                  ? 'blue'
                  : 'red'
              }
            >
              {totalAsset?.evaluationProfit} <span>%</span>
            </GreenDataDiv>
          </AssetItemDiv>
        </AssetItemContainer>
        {pathName.includes('history') && (
          <div className="flex justify-end">
            <button
              type="button"
              className="rounded border-none bg-brandColor p-2 text-brandWhite"
              onClick={handleInitCash}
            >
              원 초기화
            </button>
          </div>
        )}
      </GreenContainer>
    </TotalAssetContainer>
  )
}

export default TotalAsset
