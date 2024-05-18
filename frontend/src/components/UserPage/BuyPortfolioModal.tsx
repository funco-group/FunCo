import React, { Dispatch, SetStateAction, useEffect, useState } from 'react'
import { buyPortfolio, getCash } from '@/apis/member'
import {
  SettleModalBackgroundContainer,
  SettleModalContainer,
  SettleModalContentButtonRowDiv,
  SettleModalTitleDiv,
} from '../TradeHistory/Follow/Following/SettleModal.styled'
import BrandButtonComponent from '../Common/Button/BrandButtonComponent'
import { ContentDiv, ModalItemDiv, TitleDiv } from './BuyPortfolioModal.styled'
import { CashDiv } from './FollowModal.styled'
import AlertModal from '../Common/Modal/AlertModal'

interface BuyPortfolioModalProps {
  memberId: number
  nickname: string
  price: number
  handleBuyPortFolioClick: () => void
  setPortfolioStatus: Dispatch<SetStateAction<string>>
}

function formatDate(dateString: Date): string {
  const date = new Date(dateString)
  const year = date.getFullYear()
  const month = date.getMonth() + 1
  const day = date.getDate()

  return `${year}년 ${month}월 ${day}일`
}

function calculateTwoWeeksLater(today: Date): Date {
  const twoWeeksLater = new Date(today)
  twoWeeksLater.setDate(twoWeeksLater.getDate() + 14)
  return twoWeeksLater
}

function BuyPortfolioModal({
  memberId,
  nickname,
  price,
  handleBuyPortFolioClick,
  setPortfolioStatus,
}: BuyPortfolioModalProps) {
  const [cash, setCash] = useState<number>(0)
  const [today, setToday] = useState<string>()
  const [twoWeeksDay, setTwoWeeksDay] = useState<string>()
  const [alert, setAlert] = useState<boolean>(false)
  const [alertContent, setAlertContent] = useState<string>('')

  useEffect(() => {
    getCash((res) => {
      const { data } = res
      setCash(data.cash)
    })

    const date = new Date()
    setToday(formatDate(date))
    setTwoWeeksDay(formatDate(calculateTwoWeeksLater(date)))
  }, [])

  const closeAlert = () => {
    setAlert(false)
  }

  const clickBuyPortfolio = () => {
    if (cash < price) {
      setAlert(true)
      setAlertContent('가용 자산이 부족합니다.')
    } else {
      buyPortfolio(memberId, () => {
        setAlert(true)
        setAlertContent('포트폴리오 구독을 시작합니다.')
        setPortfolioStatus('SUBSCRIBE')
        handleBuyPortFolioClick()
      })
    }
  }

  return (
    <SettleModalBackgroundContainer>
      {alert && (
        <AlertModal
          title="알림"
          content={alertContent}
          closeAlert={closeAlert}
        />
      )}
      <SettleModalContainer width="27rem">
        <SettleModalTitleDiv>{nickname}&apos;s 포트폴리오</SettleModalTitleDiv>
        <ModalItemDiv>
          <TitleDiv>· 포트폴리오 확인 기간</TitleDiv>
          <ContentDiv>
            {today} ~ {twoWeeksDay}
          </ContentDiv>
        </ModalItemDiv>
        <ModalItemDiv>
          <TitleDiv>· 포트폴리오 가격</TitleDiv>
          <ContentDiv>
            {price.toLocaleString('ko-KR')}
            <span>WON</span>
          </ContentDiv>
        </ModalItemDiv>
        <CashDiv>
          가용 자산 : {cash?.toLocaleString('en-US')} <span>WON</span>
        </CashDiv>
        <SettleModalContentButtonRowDiv>
          <BrandButtonComponent
            content="취소"
            color={null}
            cancel
            onClick={handleBuyPortFolioClick}
            disabled={false}
          />
          <BrandButtonComponent
            content="구독"
            color={null}
            cancel={false}
            onClick={clickBuyPortfolio}
            disabled={false}
          />
        </SettleModalContentButtonRowDiv>
      </SettleModalContainer>
    </SettleModalBackgroundContainer>
  )
}

export default BuyPortfolioModal
