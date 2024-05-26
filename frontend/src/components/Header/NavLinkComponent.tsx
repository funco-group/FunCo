import Link from 'next/link'
import { usePathname } from 'next/navigation'

interface NavLinkProps {
  path: string
  name: string
}
function NavLinkComponent({ path, name }: NavLinkProps) {
  const pathname = usePathname()
  const pathIndex = name.includes('거래소') ? 2 : 1
  const isActive = pathname.split('/')[pathIndex] === path.split('/')[pathIndex]

  return (
    <Link
      href={{ pathname: path }}
      className={isActive ? 'nav-link active' : 'nav-link'}
    >
      {name}
    </Link>
  )
}

export default NavLinkComponent
