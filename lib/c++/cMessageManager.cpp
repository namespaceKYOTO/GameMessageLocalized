/*---------------------------------------------------------------------*/
/*!
 * @file	cMessageManager.cpp
 * @brief	Message Manager
 * @author	t_sato
 */
/*---------------------------------------------------------------------*/
#include "cMessageManager.hpp";

namespace message
{

cMessageManager::cMessageManager() :
	m_pData(0x00)
{
}

unsigned int cMessageManager::getDate() const
{
	if( !m_pData )	{ return ERRO_CODE_DATA_NULL; }
}

unsigned int cMessageManager::getMessageNum() const
{
}

unsigned int cMessageManager::getLanguageNum() const
{
}

}
