/*---------------------------------------------------------------------*/
/*!
 * @file	cMessageManager.hpp
 * @brief	Message Manager
 * @author	t_sato
 */
/*---------------------------------------------------------------------*/
#ifndef CMESSAGE_MANAGER__
#define CMESSAGE_MANAGER__

namespace message
{

class cMessageManager
{
public:
	const int ERRO_CODE_DATA_NULL = -1;

public:
	cMessageManager();
	~cMessageManager();
	
	unsigned int getDate() const;
	unsigned int getMessageNum() const;
	unsigned int getLanguageNum() const;

	void	getMessage(int languageNo, int messageNo, char* )

private:
	typedef struct MESSAGE_DATA
	{
		unsigned int signature;		// "TEXT"
		unsigned int date;			// creation date  YYYY MM DD (2Byte 1Byte 1Byte)
		unsigned int messagNum;		// number of message
		unsigned int langugageNum;	// number of language
		char* data
	};

private:
//	MESSAGE_DATA	*m_pData;
	char			*m_pData;
}

}	// namespace message

#endif	// CMESSAGE_MANAGER__
