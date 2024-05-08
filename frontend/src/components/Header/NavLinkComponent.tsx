import Link from 'next/link'
import { usePathname } from 'next/navigation'

interface NavLinkProps {
  path: string
  name: string
  // eslint-disable-next-line react/require-default-props
  query?: {
    type: string
  }
}
function NavLinkComponent({ path, name, query }: NavLinkProps) {
  const pathname = usePathname()
  const isActive = pathname.split('/')[1] === path.split('/')[1]

  return (
    <Link
      href={{ pathname: path, query }}
      className={isActive ? 'nav-link active' : 'nav-link'}
    >
      {name}
    </Link>
  )
}

export default NavLinkComponent
