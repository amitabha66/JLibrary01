/ $RCSfile: lpxus.msg $
/ $Date: 06-aug-2003.10:16:52 $
/ Copyright (c) 1999, 2003, Oracle.  All rights reserved.
/
/   NAME
/     lpxus.msg
/
/   DESCRIPTION
/     US-language error message file for XML parser (LPX)
/
/   NOTES
/     COMPLY WITH STANDARDS WHEN ADDING OR CHANGING MESSAGES!
/     See /vobs/rdbms/mesg/oraus.msg for full message rules.
/
/     Error messages are XML-parser-specific format strings, not general
/     printf-type format strings.  The magic character is '~' instead of
/     '%', and formatting is very restricted.  Choices are:
/
/	~d  sb4	      signed decimal output
/	~u  ub4	      unsigned decimal output
/	~x  ub4	      \u-style hexadecimal output
/	~X  ub4	      Generic 0x-style hexadecimal output
/       ~c  ub4	      single-character output
/	~s  oratext*  single/multibyte string output
/	~S  oratext*  single/multibyte/Unicode string output
/
/     Hex 'x' is for Unicode character output.  It formats "\uFFFF" for
/     USC2 characters and "\vFFFFFF" for UCS4 characters.
/
/     Hex 'X' format is used for NLS wide characters, as many bytes as
/     needed, leading 0 bytes omitted, e.g. "0xF".
/
/     For the 'S' format type, the string is expected to be in the DOM
/     data encoding, which may be single-byte, multi-byte, or Unicode.
/     For plain 's' format, the string may only be single or multi-byte.
/
/ ----------------------------------------------------------------------
/
/     For error messages with multiple arguments, the fields must be
/     identified as to the original order of arguments in the code.
/     If there is only a single argument, the number "1" may be omitted.
/
/     That is, if a message contains ~1s and ~2s then ~1s corresponds to
/     the first code argument, ~2s to the second.  This is so that the
/     order may be changed as needed in translation while the code still
/     passes the arguments in original order.  For example, the English
/
/	"only ~1d occurance(s) of element \"~2s\", minimum is ~3d"
/
/     might translate to
/
/	"\"~2s\" öðesinin sadece ~1d tekrarý var, minimum: ~3d"
/
/     but the code would remain
/
/	... LsxErrNode(scctx, n, LSXERR_MIN_OCCURS, (sb4) count,
/		       (oratext *) lsx_choice, (sb4) all->minoc_lsxall);
/
/     with the arguments passed as sb4, oratext*, sb4 (as in the US file).
/
/ ----------------------------------------------------------------------
/
/   Classification of external exception codes
/
/   00000 - 00099	Generic Errors
/   00100 - 00199	Validity Constraint Errors
/   00200 - 00299	Parser Errors
/   00300 - 00399	XSL Errors
/   00400 - 00499	XPATH Errors
/   00500 - 00599	XSLTVM Errors
/
/ --- Generic Errors ---------------------------------------------------------
/
00000, 00000, "normal, successful completion"
// *Cause:  Normal exit
// *Action: No action required.
/
00001, 00000, "NULL pointer"
// *Cause:  A NULL pointer was detected as an internal error condition.
// *Action: This is a programming error by the caller of the XML parser.
//          Please contact someone who can fix the problem.
/
00002, 00000, "out of memory"
// *Cause:  The operating system has run out of memory.
// *Action: Make more memory available to the program.
/
00003, 00000, "duplicate entry in hash table"
// *Cause:  An internal error has occurred (a key was requested to be
//          placed in a hash table but was already there).
// *Action: Contact Oracle Support Services and report the error.
/
00004, 00000, "internal error \"~s\""
// *Cause:  An internal error has occurred.
// *Action: Contact Oracle Support Services and report the error.
/
00005, 00000, "~1s buffer overflow, maximum size is ~2u bytes"
// *Cause: A name, quoted string, URL, or other document component was
//	too long.
// *Action: Restrict the component to the maximum size shown.
//
00006, 00000, "invalid child type for parent node"
// *Cause: An attempt was made to add an invalid node-type to a parent node.
// *Action: Correct the code.
/
00007, 00000, "unexpected end-of-file encountered"
// *Cause: The documented ended unexpectedly, perhaps due to truncation.
// *Action: Verify that the document is complete.
/
00008, 00000, "invalid memory callback"
// *Cause: The memory callback structure passed to xmlinit was missing
//	the allocate or free functions (or both).
// *Action: Provide both functions in the callback structure.
/
00009, 00000, "In line ~1u of ~2s:"
00010, 00000, "In line ~1u of ~2s [parameter entity ~3S]:"
00011, 00000, "In line ~1u of ~2s [general entity ~3S]:"
// *Cause: Error message prefixes, not errors themselves. These are the
//	possible banner messages which appear before an XML error to
//	describe the position (line#) and source (buffer or URI) in which
//	the error occurred, as well as the entity's name (if the source is
//	an entity). Parameter entity banner must be at +1 to non-entity
//	banner, general entity at +2 (see lpxerr.c).
// *Action: No action required. This is not an error message.
/
00012, 00000, "Unicode data alignment error"
// *Cause: An input Unicode (UCS2) datum was not aligned properly.
// *Action: UCS2 data consists of an array of shorts (ub2) which must
//	be aligned on an even-byte boundary.
/
00013, 00000, "wrong node type"
// *Cause: The wrong node type was given as argument to a DOM call.
// *Action: Review the failing function call, consult the documentation, and
//	make sure the node types passed as correct.
/
00014, 00000, "context is not clean"
// *Cause: An operation was performed on a context that has already been
//	used (so is not "clean").
// *Action: Some functions must be performed on a newly initialized context
//	before being used for parsing. For example, setting a shared DTD.
//	Change the code and do the call before parsing.
//
00015, 00000, "XML Location: "
00016, 00000, "XSL Location: "
00017, 00000, "~1sNodeName: <~2S> "
// *Cause: More error message boilerplate.
// *Action: No action required. This is not an error message.
/
00018, 00000, "internal error \"nested open strings\""
// *Cause:  An internal error has occurred.
// *Action: Contact Oracle Support Services and report the error.
/
00019, 00000, "property \"~s\" unknown"
// *Cause:  The named initialization property was unknown
// *Action: Refer to the documentation for the complete set of
//	init properties.
/
/ --- Validity Constraint Errors ---------------------------------------------
/
00100, 00000, "root element \"~1S\" does not match DTD root \"~2s\""
// *Cause:  Validity Constraint 2.8 failed:
//	"The Name in the document type declaration must match the element
//	type of the root element."
// *Example:
//	<?xml version="1.0"?>
//	<!DOCTYPE greeting [ <!ELEMENT greeting (#PCDATA)> ]>
//	<salutation>Hello!</salutation>
// *Explanation:
//      The document's root element, salutation, does not match the
//	root element declared in the DTD (greeting).
// *Action: Correct the document.
/
00101, 00000, "parameter-entity markup cannot be split up"
// *Cause:  Validity Constraint 2.8 failed:
//	"Parameter-entity replacement text must be properly nested with
//	markup declarations."
// *Example:
//	<?xml version="1.0"?>
//	<!DOCTYPE greeting [
//	    <!ENTITY % e "<!ELEMENT ">
//	    %e; greeting (#PCDATA)>
//	]>
//	<greeting>Hello!</greeting>
// *Explanation:
//	The parameter entity 'e' contains markup which may not be split
//	up; the entire ELEMENT (or ATTLIST or ENTITY) definition must be
//	present in one piece.
// *Action: Correct the document.
/
00102, 00000, "standalone document declaration should be \"no\""
// *Cause:  Validity Constraint 2.9 failed:
//	"Standalone document declaration must have the value 'no' if any
//	external markup declarations contain declarations of:
//	    * attributes with default values  - or -
//	    * entities  - or -
//	    * attributes with values subject to normalization  - or -
//	    * element types with element content"
// *Action: Correct the document.
/
00103, 00000, "document structure does not match DTD"
// *Cause:  Validity Constraint 3 failed:
//	"An element is valid if there is a declaration matching elementdecl
//	where the Name matches the element type, and one of the following
//	holds:
// 
//	* The declaration matches EMPTY and the element has no content
//	* The declaration matches children and the sequence of child elements
//	  belongs to the language generated by the regular expression in the 
//	  content model, with optional white space (characters matching the
//	  nonterminal S) between each pair of child elements.
//	* The declaration matches Mixed and the content consists of character
//	  data and child elements whose types match names in the content model.
//	* The declaration matches ANY, and the types of any child elements 
//	  have been declared."
// *Action: Correct the document.
/
00104, 00000, "element \"~S\" is not declared in the DTD"
// *Cause:  Validity Constraint 3 failed:
//	The named element has no matching elementdecl in the DTD
// *Action: Correct the document.
/
00105, 00000, "element \"~S\" is not empty as required by the DTD"
// *Cause:  Validity Constraint 3 failed:
//	The named element is declared as EMPTY in the DTD but contains
//	sub-elements in the document.
// *Action: Correct the document.
/
00106, 00000, "attribute \"~1S\" of element \"~2S\" is undefined"
// *Cause:  Validity Constraint 3.1 failed:
// *Action: Correct the document.
/
00107, 00000, "element \"~S\" has multiple declarations"
// *Cause:  Validity Constraint 3.2 failed:
//	"No element type may be declared more than once."
// *Action: Correct the DTD.
/
00108, 00000, "parameter-entity parenthetical cannot be split up"
// *Cause:  Validity Constraint 3.2.1 failed:
//	"Parameter-entity replacement text must be properly nested with
//	parenthesized groups.  For interoperability, if a parameter-entity
//	reference appears in a choice, seq, or Mixed construct, its
//	replacement text should not be empty, and neither the first nor
//	last non-blank character of the replacement text should be a
//	connector (| or ,).
// *Example:
//	<?xml version="1.0"?>
//	<!DOCTYPE foo [
//	    <!ELEMENT greeting (#PCDATA)>
//	    <!ENTITY % e "(#PCDATA|">
//	    <!ELEMENT foo %e; greeting)>
//	]>
//	<greeting>Hello!</greeting>
// *Explanation:
//	The parenthetical content of the 'e' entity may not be split up
//	into sections: both open and close parentheses must be in the same
//	declaration.
// *Action: Correct the DTD.
/
00109, 00000, "duplicate name \"~S\" in mixed-content declaration"
// *Cause:  Validity Constraint 3.2.2 failed:
//	"The same name must not appear more than once in a single
//	mixed-content declaration."
// *Example:
//	<!ELEMENT p (#PCDATA|a|b|c|d|a)>
// *Explanation:
//	'a' occurs more than once in the mixed-content declaration.
// *Action: Correct the DTD.
/
00110, 00000, "invalid ~1s \"~2S\" (not a Name)"
// *Cause:  Validity Constraint 3.3.1 failed: Given thing is not a Name
// *Action: Correct the document.
/
00111, 00000, "invalid ~1s \"~2S\" (not a Nmtoken)"
// *Cause:  Validity Constraint 3.3.1 failed: Given thing is not a Nmtoken
// *Action: Correct the document.
/
00112, 00000, "element \"~S\" has multiple ID attributes"
// *Cause:  Validity Constraint 3.3.1 failed:
//	"No element type may have more than one ID attribute specified."
// *Action: Correct the document.
/
00113, 00000, "element \"~1S\" ID attribute \"~2S\" must be #IMPLIED or #REQUIRED"
// *Cause:  Validity Constraint 3.3.1 failed:
//	"An ID attribute must have a declared default of #IMPLIED or
//	#REQUIRED."
// *Action: Correct the document.
/
00114, 00000, "element \"~1S\" attribute \"~2S\" has invalid enumeration value \"~3S\""
// *Cause:  Validity Constraint 3.3.1 failed:
//	"Values of this type must match one of the Nmtoken tokens in the 
//	declaration."
// *Action: Correct the document.
/
00115, 00000, "element \"~1S\" is missing required attribute \"~2S\""
// *Cause:  Validity Constraint 3.3.2 failed:
//	"If the default declaration is the keyword #REQUIRED, then the 
//	attribute must be specified for all elements of the type in the
//	attribute-list declaration."
// *Action: Correct the document.
/
00116, 00000, "element \"~1S\" attribute \"~2S\" has invalid value \"~3S\", must be \"~4S\""
// *Cause:  Validity Constraint 3.3.2 failed:
//	"If an attribute has a default value declared with the #FIXED
//	keyword, instances of that attribute must match the default value."
// *Action: Correct the document.
/
00118, 00000, "undefined entity \"~S\""
// *Cause:  Validity Constraint 4.1 failed:
//	"In a document with an external subset or external parameter entities
//	with "standalone='no'", the Name given in the entity reference must
//	match that in an entity declaration."
// *Action: Correct the document.
/
00119, 00000, "element \"~1S\" attribute \"~2S\" must be an unparsed entity"
// *Cause: The attribute value must be an unparsed entity.
// *Action: Correct the document.
/
00120, 00000, "entity \"~1S\" NDATA (notation) \"~2S\" is undefined"
// *Cause: Entity's NDATA (notation) is undefined
// *Action: Correct the document.
/
00121, 00000, "undefined notation \"~S\""
// *Cause: Notation is not known.
// *Action: Correct the document.
/
00122, 00000, "undefined ID \"~S\" in IDREF"
// *Cause:  Validity Constraint 3.3.1 failed:
//	"A name must not appear more than once in an XML document as a
//	value of this type; i.e., ID values must uniquely identify the
//	elements which bear them."
// *Action: Correct the document.
/
00123, 00000, "duplicate ID \"~S\""
// *Cause: An ID was used twice, they must be unique.
// *Action: Correct the document.
/
00124, 00000, "attribute value should be one or more tokens"
// *Cause: An attribute with tokenized type (IDREFS, ENTITIES, NMTOKENS)
//	did not contain any tokens.
// *Action: Value must contain one or more tokens, separated by spaces.
/
00125, 00000, "duplicate entity \"~S\" (ignored)"
// *Cause: A general or parameter entity was declared more than once.
// *Action: This is a warning, not an error. The first entity declaration is in
//	force, subsequent are ignored. Remove duplicate entity declarations
//	or ignore the warning.
/
//00126 no error message, return value XMLERR_NO_DECL only
// *Cause: Warning returned by XmlDomGetDecl when original document did
//	not contain an XMLDecl
// *Action: This is a warning, not an error.  The value XMLERR_NO_DECL is
//	used by the programmer to detect whether a document had an XMLDecl
//	or not.  It does not indicate a problem.
/
/ --- Parser Errors ----------------------------------------------------------
/
00200, 00000, "could not convert from encoding ~1s to ~2s"
// *Cause:  The conversion cannot be made between the specified encodings.
// *Action:  Choose a data encoding which can represent all expected
//	input encoding (such as a Unicode-based encoding, UTF-8 or UTF-16).
/
00201, 00000, "unknown encoding \"~s\""
// *Cause:  The specified encoding was not known. It should be an IANA
//	or Oracle encoding name.
// *Action:  Use an appropriate encoding.
/
00202, 00000, "could not open \"~s\" (error ~u)"
// *Cause:  The named input (file, URL, etc) does not exist.
// *Action:  Make sure the named input is available and can be opened.
/
00203, 00000, "could not read from \"~s\" (error ~u)"
// *Cause:  Data could not be read from the named input.
// *Action:  Take appropriate action to allow data to be read.
/
00204, 00000, "syntax error"
// *Cause:  A syntax error was found.
// *Action:  Check the XML document line and correct it.
/
00205, 00000, "expected \"<!--\" at the start of comment"
// *Cause:  Bad syntax detected when processing a comment.
// *Action:  Fix the comment syntax.
/
00206, 00000, "invalid CDATA section"
// *Cause:  Bad syntax detected when processing CDATA. Proper format
//	    is '<![CDATA[' data ']]>'.
// *Action:  Correct the CDATA syntax.
/
00207, 00000, "expected \"[\" at the start of conditional section"
// *Cause:  Bad syntax detected when processing a conditional section.
// *Action:  Fix the conditional section syntax.
/
00208, 00000, "unknown DTD keyword \"~s\""
// *Cause:  An unknown keyword was found in the DTD.
// *Action:  Use a proper keyword.
/
00209, 00000, "PI names starting with XML are reserved"
// *Cause:  Processing instruction starting with XML was found.
// *Action:  Use another name for the processing instruction.
/
00210, 00000, "expected '~1c' instead of '~2c'"
// *Cause:  A syntax error was detected.
// *Action:  Use proper syntax.
/
00211, 00000, "attribute default must be REQUIRED, IMPLIED, or FIXED"
// *Cause:  Attribute default was invalid.
// *Action:  Default must be REQUIRED, IMPLIED, or FIXED.
/
00212, 00000, "comment must not contain \"--\""
// *Cause:  A syntax error was detected in the comment.
// *Action:  Use '--' only when specifying the end of the comment.
/
00213, 00000, "comment did not end in \"-->\""
// *Cause:  A syntax error was detected in the comment.
// *Action:  Be sure to end the comment with '-->'.
/
00214, 00000, "CDATA section did not end in \"]]>\""
// *Cause:  A syntax error was detected in the CDATA section.
// *Action:  Be sure to end the CDATA section with ']]>'.
/
00215, 00000, "processing instruction did not end in \"?>\""
// *Cause:  A syntax error was detected in the PI section.
// *Action:  Be sure to end the PI with '?>'.
/
00216, 00000, "invalid character ~1u (~2X)"
// *Cause:  An invalid multibyte character was found.
// *Action:  Use only characters allowed by the XML specification.
/
00217, 00000, "invalid character ~1u (~2x)"
// *Cause:  An invalid Unicode character was found.
// *Action:  Use only characters allowed by the XML specification.
/
00218, 00000, "invalid character ~1u ('~2c')"
// *Cause:  An invalid native (ASCII/EBCDIC) character was found.
// *Action:  Use only characters allowed by the XML specification.
/
00219, 00000, "invalid digit '~c' in character reference"
// *Cause:  An invalid digit was found in a character reference.
// *Action: Character references are either &#DDD; where D's are decimal
//	    digits, or &#xHHH; where H's are hexadecimal digits.
/
00220, 00000, "the string \"]]>\" cannot occur in character data"
// *Cause:  Found ']]>' in character data.
// *Action:  Do not use ']]>' in character data.
/
00221, 00000, "the character \"<\" cannot occur in attribute values"
// *Cause:  Found '<' in an attribute value.
// *Action:  Do not use '<' in attribute values.
/
00222, 00000, "error received from SAX callback function"
// *Cause:  An error was received from the SAX callback function.
// *Action:  Examine the additional error messages and take corrective action.
/
00223, 00000, "external entity \"~s\" found in an attribute value"
// *Cause:  An external entity reference was found in an attribute value.
// *Action:  Use only references to internal or character entities in 
//           attribute values.
/
00224, 00000, "multiple occurrences of attribute \"~S\" found"
// *Cause:  An attribute occurred multiple times in the same start-tag or
//          empty-element tag.
// *Action:  Make sure that the attributes are unique.
/
00225, 00000, "end-element tag \"~1S\" does not match start-element tag \"~2S\""
// *Cause:  An element tag was not ended properly.
// *Action:  Make sure that the correct end element tag is used.
/
00226, 00000, "entity \"~S\" is not declared"
// *Cause:  An entity is not declared.
// *Action:  Declare the entity before referencing it.
/
00227, 00000, "entity \"~S\" is not a parsed entity"
// *Cause:  An entity reference contained the name of an unparsed entity.
// *Action:  Only reference parsed entities.
/
00228, 00000, "entity reference \"~S\" refers to itself"
// *Cause:  An entity reference contains a recursive reference to itself.
// *Action:  Modify the contents of the entity reference to remove this
//           recursion.
/
00229, 00000, "input source is empty"
// *Cause:  An XML input file has no contents.
// *Action:  The XML file representing a document must contain at least one
//           element.
/
00230, 00000, "invalid character ~1u (~2x) found in a Name or Nmtoken"
// *Cause:  An invalid character was found in a NAME or NMTOKEN.
// *Action:  Use only the characters allowed for NAMES and NMTOKENS by the XML
//           specification.
/
00231, 00000, "invalid character ~1u ('~2c') found in a Name or Nmtoken"
// *Cause:  An invalid character was found in a NAME or NMTOKEN.
// *Action:  Use only the characters allowed for NAMES and NMTOKENS by the XML
//           specification.
/
00232, 00000, "invalid use of a parameter entity reference"
// *Cause:  A parameter entity reference was found in an improper location in
//          the internal DTD subset.
// *Action:  Use a parameter entity reference only where markup declarations
//           can occur in the internal DTD subset, in the external DTD subset,
//           or in an external entity.
/
00233, 00000, "namespace prefixes starting with \"xml\" are reserved"
// *Cause:  Namespace prefix starting with XML was found.
// *Action:  Use another name for the namespace prefix.
/
00234, 00000, "namespace prefix \"~S\" is not declared"
// *Cause:  Namespace prefix is not declared.
// *Action:  Declare the prefix in an attribute list.
/
00235, 00000, "invalid XML version, must be 1.0 or 2.0"
// *Cause:  Only versions 1.0 and 2.0 of the XML specification are supported.
// *Action:  Use the 1.0 or 2.0 specification & set version# accordingly.
/
00236, 00000, "invalid character ~1u ('~2c') found in public identifier"
// *Cause:  An invalid character was found in a public identifier.
// *Action:  Use only the characters allowed for public identifiers by the XML
//           specification.
/
00237, 00000, "invalid condition section keyword, must be INCLUDE or IGNORE"
// *Cause: A conditional section <![ keyword [ markup ]]> had invalid keyword,
//	   must be either "IGNORE" or "INCLUDE"
// *Action: Correct condition section usage in document.
/
00238, 00000, "unterminated conditional section"
// *Cause: A conditional section was not properly terminated with ]]>.
// *Action: Verify conditional nesting in document and correct.
/
00239, 00000, "invalid attribute type \"~s\""
// *Cause: The attribute type is not valid. Options are CDATA, ID, IDREF,
//	   IDREFS, ENTITY, ENTITIES, NMTOKEN, or NMTOKENS.
// *Action: Check and correct attribute declaration.
/
00240, 00000, "element-start tag is not well formed"
// *Cause: A start-element tag was improperly formed.
// *Action: Check and correct the start-element syntax.
/
00241, 00000, "entity reference is not well formed"
// *Cause: An entity reference (general or parameter) was not formed properly.
// *Action: Form entity reference correctly as "&name;" or "%name;".
/
00242, 00000, "invalid use of ampersand ('&') character (use &amp;)"
// *Cause: The ampersand character is used only to start entity or character
//	   references.
// *Action: To include an ampersand character as data, use the built-in
//	    &amp; general entity.
/
00243, 00000, "element attribute value must be enclosed in quotes"
// *Cause: An attribute defined in an element's start-tag must be enclosed
//	   in single ('') or double ("") quotes.
// *Action: Enclose the attribute value in quotes.
/
00244, 00000, "invalid use of less-than ('<') character (use &lt;)"
// *Cause: The less-than character ('<') is not permitted as data.
// *Action: Use the built-in entity &lt; instead. Check for mismatched
//	    quotes ("') in case the '<' is part of subsequent markup.
/
00245, 00000, "extra data after end of document"
// *Cause: After the close of the top-level element, more data was found.
// *Action: The end-element tag for the top-level element must be the
//	    last thing in the document.
/
00246, 00000, "missing system ID after public ID"
// *Cause: In an external ID declaration, the public ID literal was
//	   not followed by the system ID literal as required.
// *Action: Provide with public *and* system IDs for 'PUBLIC' type.
/
00247, 00000, "invalid Document Type Declaration (DTD)"
// *Cause: Problems were encountered in the DTD declaration.
// *Action: Review the DTD and correct the problems.
/
00248, 00000, "invalid entity declaration"
// *Cause: Problems were encountered parsing an entity declaration.
// *Action: Check and correct the declaration syntax.
/
00249, 00000, "invalid external ID declaration"
// *Cause: Problems were encountered parsing an external ID declaration.
// *Action: Check and correct the declaration syntax.
/
00250, 00000, "invalid attribute declaration"
// *Cause: Problems were encountered parsing an attribute declaration.
// *Action: Check and correct the declaration syntax.
/
00251, 00000, "conditional sections are valid only in external DTDs"
// *Cause: A condition section is not permitted in internal DTDs.
// *Action: Remove the condition section.
/
00252, 00000, "invalid entity replacement-text nesting"
// *Cause: Markup included from an entity must nest/group properly. That
//	   is, open/close markup must occur within the same entity.
//	   For example, 
//		<!DOCTYPE doc [ <!ENTITY e "</foo><foo>"> ]>
//		<doc><foo>&e;</foo></doc>
//	   Is invalid since foo's start-tag occurs in the top-level
//	   document, but the close-tag is provided by the "e" entity.
//	   Both start and end must be provided by the same source.
// *Action: Examples, such as the ones above, are not permitted.
/
00253, 00000, "missing required version number in XML declaration"
// *Cause: An XML declaration was missing the required version#.
// *Action: Always provide a version#, which must come first in the
//	    declaration. XML is case sensitive, so only "version"
//	    (not "Version", and so on) is valid.
/
00254, 00000, "invalid XML declaration"
// *Cause: Problems were encountered parsing an XML declaration.
// *Action: Check and correct the declaration syntax.
/
00255, 00000, "XML standalone declaration must be \"yes\" or \"no\""
// *Cause: The "standalone" parameter in the XML declaration had an
//	   invalid value.
// *Action: standalone must be set to either "yes" or "no". Case is
//	    sensitive, so "Yes", "YES", and so on, are invalid.
00256, 00000, "invalid element declaration"
// *Cause: Problems were encountered parsing an element declaration.
// *Action: Check and correct the declaration.
/
00257, 00000, "invalid children specification in element declaration"
// *Cause: The 'children' specification in an element declaration was
//	   invalid.
// *Action: Check and correct the declaration.
/
00258, 00000, "invalid \"Mixed\" specification in element declaration"
// *Cause: The 'Mixed' specification in an element declaration was
//	   invalid.
// *Action: Check and correct the declaration.
/
00259, 00000, "invalid notation declaration"
// *Cause: Problems were encountered parsing a notation declaration.
// *Action: Check and correct the declaration syntax.
/
00260, 00000, "invalid xml:space attribute declaration"
// *Cause: The xml:space attribute must be declared as an enumeration
//	with choices "default" and "preserve". For example,
//	<!ATTLIST foo xml:space (default|preserve) 'preserve').
// *Action: Declare the special attribute as above.
/
00261, 00000, "invalid URL ~s"
// *Cause: The specified URL was invalid and could not be parsed.
// *Action: Correct the URL; consult RFC-2396.
/
00262, 00000, "unsupported protocol ~s"
// *Cause: An URL was encountered which requested a protocol not supported
//	by the XML parser. Only HTTP and file are currently allowed.
// *Action: Make the data available through the filesystem or HTTP.
/
00263, 00000, "couldn't connect to host ~s port ~d"
// *Cause: A TCP connection couldn't be opened to the named host.
// *Action: Verify the hostname and connectivity to the host.
/
00264, 00000, "send failed to host ~s"
// *Cause: An error occurred trying to send data over a TCP connection.
// *Action: Verify network connectivity, and so on.
/
00265, 00000, "read failed from to host ~s"
// *Cause: An error occurred trying to read data from a TCP connection.
// *Action: Verify network connectivity, and so on.
/
00266, 00000, "invalid language specification ~s"
// *Cause: The given language specification was invalid.
// *Action: Language specification has the format
//	<language>_<territory>.<character set>.
//	For example, "French_France".
/
00267, 00000, "could not resolve relative URL ~s"
// *Cause: The named relative URL couldn't be resolved against its parent.
// *Action: Make sure the relative makes sense in relation to its parent
//	URL; see RFC-2396 sections 4, 5, and appendix C.
/
00268, 00000, "invalid access method ~1d, must be 0 to ~2d"
// *Cause: The provided access code was not in the valid range.
// *Action: The code should be one of the XMLACCESS_xxx codes defined in 
//	oraxml.h, in the range shown in the error message.
/
00269, 00000, "all three access functions (open/close/read) must be provided"
// *Cause: An attempt was made to set the access method callbacks, but all three
//	functions were not provided.
// *Action: All three callback functions (open, close, and read) are required.
//	They must all be provided, even if they are stub functions which do
//	nothing.
/
00270, 00000, "FTP error: ~s"
// *Cause: An error was returned from the FTP server while trying to
//	retrieve a file. See the specific message for details.
// *Action: Corrective action depends on the error.
/
00271, 00000, "FTP login failed: ~s"
// *Cause: The username/password combination was invalid for FTP login.
// *Action: Specify a valid pair.
/
00272, 00000, "FTP server unavailable: ~s"
// *Cause: The FTP server is unavailable for use.
// *Action: No action is possible from the client side. See server's error message.
/
00273, 00000, "failed to initialize TCP/IP"
// *Cause: The TCP/IP package could not be initialized.
// *Action: Check with system administrator to see if this is a configuration
//	problem or a connectivity problem.
/
00274, 00000, "can't import node type"
// *Cause: Some node types (DOCUMENT_NODE & DOCUMENT_TYPE_NODE) cannot
//	be imported with importNode().
// *Action: No action required.
/
00275, 00000, "can't set output/data encoding AFTER parsing"
// *Cause: Output/data encoding must be set after initialization but
//	BEFORE any parsing has taken place.
// *Action: Set encoding before parsing any documents.
/
00276, 00000, "bad HTTP/Mime header"
// *Cause: An HTTP reply contained an invalid Mime header.
// *Action: Verify HTTP reply for accuracy. See RFC 2616.
/
00277, 00000, "no closing quote was seen"
// *Cause:  A quoted string was started but not finished.
// *Action:  Put a closing quote in the proper location.
/
00278, 00000, "invalid ~s proxy \"~s\""
// *Cause: The proxy specification for the given protocol was invalid.
// *Action: Check and correct the proxy specification. For HTTP, this
//	is the environment variable "http_proxy".
/
00279, 00000, "invalid no_proxy \"~s\""
// *Cause: The no_proxy specification was invalid.
// *Action: Check and correct it. no_proxy is a comma- or space-separated
//	list of machine or domain names, with an optional port part. If
//	no port part is present, then it applies to all ports on that domain.
/
00280, 00000, "HTTP error ~s"
// *Cause: An HTTP protocol error occurred.
// *Action: Corrective action depends on the error..
/
00281, 00000, "unsupported encoding \"~s\""
// *Cause:  The specified encoding is known but not supported by the parser.
// *Action:  Try a different encoding.
/
00282, 00000, "document cannot have both internal/external and shared DTDs"
// *Cause: A parser context which had a shared DTD set was used to parse
//	a document which also contained a DTD. You cannot use both a
//	shared DTD and and internal/external one.
// *Action: If a shared DTD is to be used, then the documents parsed must not
//	contain or reference a DTD.
/
00283, 00000, "document encoding is ~s-based but default input encoding is not"
// *Cause: The input document was detected to be ASCII (or EBCDIC) based, but
//	no encoding was specified in the XMLDecl and the default input coding
//	was not ASCII (or EBCDIC) based, so could not be applied.
// *Action: Add an explicit encoding specification to the XMLDecl so the
//	default input encoding is not needed, or pick a default encoding
//	which matches the input document.
/
00284, 00000, "namespace prefix to NULL URI is not allowed"
// *Cause: An element's namespace prefix declarations was for a NULL URI,
//	e.g. <foo xmlns:bar=""/>
//	This is illegal presently in XML 1.0, but will be legal in XML 1.1
// *Action: Remove erroneous prefix definition.
/
00285, 00000, "invalid Unicode surrogate ~X ~X"
// *Cause: A Unicode document contained an invalid surrogate.  If the first
//	(high) surrogate is in the correct range 0xD800 to 0xDBFF, then the
//	second (low) surrogate must be in the range 0xDC00 to 0xDFFF.
// *Action: Correct the document.
/
/ --- XSL Errors ----------------------------------------------------------
/
00300, 00000, "no name in attribute set"
// *Cause:  The name attribute was not found in the attribute-set element.
// *Action:  Add a name attribute for this element.
/
00301, 00000, "error in XPATH evaluation"
// *Cause:  The XPATH evaluation returns an error.
// *Action:  Check specified XPATH expression to determine the error.
/
00302, 00000, "Incorrect stylesheet. The node is not valid."
// *Cause: The child node is of invalid type or has invalid name for this
//         particular location in stylesheet, rendering the stylesheet
//         as invalid XSLT.
// *Action:  Fix the stylesheet by using valid nodes only.
/
00303, 00000, "attribute value \"~S\" not expected for ~S"
// *Cause:  Attribute is found but its value is not the expected value.
// *Action:  Set the attribute value to the correct value.
/
00304, 00000, "input parameter to function is null"
// *Cause:  An input parameter passed into this function is null when 
//          it is not supposed to.
// *Action:  Make sure the caller function is not passing null for this
//           parameter.
/
00305, 00000, "missing token"
// *Cause:  An expected token is not found.
// *Action:  Check the input string to make sure the expected token is present.
/
00306, 00000, "inputed string ended with no corresponding closing \'}\'"
// *Cause:  A closing '}' is expected.
// *Action:  Add the closing '}' to the input string or remove the extra 
//           opening '{'.
/
00307, 00000, "namespace prefix ~S used but not declared"
// *Cause:  Namespace prefix is used but not declared.
// *Action:  Either declare the namespace or don't use this namespace prefix.
/
00308, 00000, "attribute ~S not found in ~S"
// *Cause:  The expected attribute for this node is not found.
// *Action:  Need to add this attribute to the node.
/
00309, 00000, "cannot initialize XPATH"
// *Cause:  XPATH context could not be initialized.
// *Action:  Check the initialization function LpxsutInitXpathCtx().
/
00310, 00000, "element ~S not found in ~S"
// *Cause:  The expected element is not found.
// *Action:  Check the initialization function LpxsutInitXpathCtx().
/
00311, 00000, "unsupported feature: ~S"
// *Cause:  This feature is not supported.
// *Action:  Do not use this feature.
/
00312, 00000, "cannot construct XML PI with content: ~S"
// *Cause:  The content of XML PI node might be invalid.
// *Action:  Make necessary changes to make the node valid according to spec.
/
00313, 00000, "cannot construct XML comment with content: ~S"
// *Cause:  The content of XML comment node might be invalid.
// *Action:  Make necessary changes to make the node valid according to spec.
/
00314, 00000, "an internal failure occurred"
// *Cause:  An internal error occurred in the code.
// *Action:  Contact appropriate developer.
/
00315, 00000, "extension function ~S not supported"
// *Cause:  This extension function is not supported.
// *Action:  Either contact appropriate developer for more information or
//           don't use this extension function.
/
00316, 00000, "invalid value ~S for ~S attribute ~S"
// *Cause: The value for the specified attribute is invalid.
// *Action: Consult the XSL spec, use only legal values.
/
00317, 00000, "undefined decimal-format \"~S\""
// *Cause: The named decimal-format is undefined (the name "#default"
//	means the default format).
// *Action: Define the desired decimal-format before trying to use it.
/
00318, 00000, "duplicate xsl:decimal-format \"~S\""
// *Cause: The named decimal-format was declared more than once.
// *Action: Make sure there is only a single declaration.
/
00319, 00000, "The node specified is not valid"
// *Cause: The node specified is not of expected type.
// *Action: Use only the nodes of legal type.
/
00320, 00000, "No more attributes can be added to a non empty element"
// *Cause: The element to which an attribute was being added is non
//   empty and hence can not add anymore attributes to it.
// *Action: Modify the XSLT stylesheet so that all the attributes
//   are added to an element before anything else is added to it.
//   If you absolutely can not do that then select your output
//   method to be DOM based rather then a stream or SAX based
//   output which you are using presently.
/
00321, 00000, "None of the output method (DOM, SAX, Stream) is selected"
// *Cause: User is trying to process an XML file with out selecting any
//   mechanism for output.
// *Action: User must select one of the output mechanisms (SAX/DOM/Stream)
//   before attempting to process the XML file.
/
00322, 00000, "A doc referred by XSLT stylesheet could not be opened : ~s"
// *Cause: Either an import,include or document() function tried to
//   open a document and failed.
// *Action: Make sure that document is present and can be opened.
/
00323, 00000, "illegal apply-imports because of no current template: ~s"
// *Cause: apply-imports was used even when there was no current template
//   possibly with in for-each.
// *Action: Make sure that apply-imports are invoked only if there is 
//   a current template and it is not in a xsl:for-each.
/
00324, 00000, "\"~S\" is not a valid value for the lang attribute of xsl:sort"
// *Cause:  An invalid language name was specified for sorting. 
// *Action: Provide a valid value for the lang attribute of xsl:sort.
/
/ --- XPATH Errors ----------------------------------------------------------
/
00400, 00000, "an internal error has occurred in XPATH"
// *Cause:  An internal error has occurred in XPATH.
// *Action: Contact the appropriate developer.
/
00401, 00000, "invalid QName in the XSL file"
// *Cause:  An invalid QName was passed to the XPATH parser.
// *Action:  See whether there are any invalid QNames in the XSL file.
/
00402, 00000, "invalid axisname in the XSL file"
// *Cause: An invalid axis name was passed to the XPATH parser.
// *Action: See whether all axis names in the XSL file are correct. 
/
00403, 00000, "unmatched quote in the XSL file"
// *Cause:  An unmatched quote was found in the XSL file.
// *Action:  Check for unmatched quotes in the XSL file.
/
00404, 00000, "unable to resolve namespace URI"
// *Cause:  The namespace URI may not be valid.
// *Action:  Make sure that the name space URIs are valid.
/
00405, 00000, "unable to allocate memory"
// *Cause:  May be out of memory.
// *Action: Try increasing virtual memory. 
/
00406, 00000, "object of incorrect type passed to the function"
// *Cause:  An object of incorrect type was passed to the XPATH/XSL function.
// *Action:  Do the required conversion before passing in an object of
//           type which is different from the expected type.
/
00407, 00000, "right square bracket missing in the XSL file"
// *Cause:  Right square bracket missing in the XSL file.
// *Action: Check for unmatched square bracket in the XSL file.
/
00408, 00000, "right parenthesis missing in the XSL file"
// *Cause:  Right parenthesis missing in the XSL file.
// *Action:  Check for unmatched parenthesis in the XSL file.
/
00409, 00000, "incorrect token encountered while parsing"
// *Cause:  An unexpected token encountered while parsing the expression/
//          pattern.
// *Action:  Check the syntax of the expressions/patterns.
/
00410, 00000, "unable to resolve the variable reference"
// *Cause:  Variable reference may not be valid.
// *Action:  Check whether the variable references are valid.
/
00411, 00000, "unknown function name encountered"
// *Cause:  The function is not supported at this time or the name is invalid.
// *Action:  Check the function names. If valid and this error occurs, then 
//           avoid using them at this time.
/
00413, 00000, "loss of precision due to excessively large numerical constant"
// *Cause:  The numerical constant in the XPath expression is too big.
// *Action:  Use a string constant instead if possible.
/
00601, 00000, "Invalid token in: '~S'"
// *Cause:  Invalid token in XPath expression.
// *Action:  Check the expression.
/
00602, 00000, "Invalid child element '~1S' of element '~2S'."
// *Cause:  Invalid child element in this stylesheet context.
// *Action:  Check the stylesheet.
/
00603, 00000, "Invalid attribute value '~1S': {element '~2S', attribute '~3S'}."
// *Cause:  Invalid attribute value in this stylesheet context.
// *Action:  Check the stylesheet.
/
00604, 00000, "Invalid attribute value '~1S', for attribute '~2s'."
// *Cause:  Invalid attribute value for this attribute.
// *Action:  Check the stylesheet.
/
00605, 00000, "Invalid attribute '~1S' in element '~2S'."
// *Cause:  Invalid attribute for this element.
// *Action:  Check the stylesheet.
/
00606, 00000, "Missing attribute '~1s' in element '~2S'."
// *Cause:  Missing attribute for this element.
// *Action:  Check the stylesheet.
/
00607, 00000, "Invalid reference: '~S'."
// *Cause:  Invalid variable or parameter or template reference.
// *Action:  Check the stylesheet.
/
00608, 00000, "Repeated declaration of '~1S' in element '~2S'."
// *Cause:  Only one declaration is allowed at this level.
// *Action:  Check the stylesheet.
/
00609, 00000, "Function call with invalid number of arguments in '~1S'."
// *Cause:  Invalid number of arguments.
// *Action:  Check the function signature.
/
00651, 00000, "VM Stack overflow."
// *Cause:  The XML data is too large.
// *Action:  Increase the size of VM stacks in XmlXslVMCreate().
/
00652, 00000, "SAX callback returns with error."
// *Cause:  SAX callback returns an error.
// *Action:  Check the callback function.
/
00653, 00000, "Output attribute '~S' doesn't have a parent element."
// *Cause:  Attribute is generated in a wrong context.
// *Action:  Check the XSLT stylesheet.
/
00654, 00000, "Output namespace attribute '~S' doesn't have a parent element."
// *Cause:  Attribute generated in the wrong context.
// *Action:  Check the XSLT stylesheet.
/
00655, 00000, "Invalid output comment '~S'."
// *Cause:  Invalid comment node.
// *Action:  Check the XSLT stylesheet.
/
00656, 00000, "XSLTVM terminate."
// *Cause:  VM terminates.
// *Action:  No action.
/
00657, 00000, "Invalid output PI '~S'."
// *Cause:  Invalid PI node.
// *Action:  Check the XSLT stylesheet.
/
00658, 00000, "Invalid XSLT object type."
// *Cause:  Invalid object type in XPath evaluation.
// *Action:  Check the XSLT stylesheet.
/
00659, 00000, "Output write failed."
// *Cause:  Write operation failed.
// *Action:  Check the write method.
/
00660, 00000, "Not a well-formed document or external entity."
// *Cause:  The generated document is not well-formed.
// *Action:  Check the XSLT stylesheet.
/
00661, 00000, "Failed to load: '~s'."
// *Cause:  Failed to load a document.
// *Action:  Check the XSLT stylesheet.
/
00662, 00000, "Invalid encoding."
// *Cause:  Invalid encoding specified.
// *Action:  Check the encoding string.
/
00663, 00000, "VM String-Stack overflow."
// *Cause:  The string data is too large.
// *Action:  Increace the size of VM StringStack in XmlXslVMCreate().
/
00664, 00000, "VM Node-Stack overflow."
// *Cause:  Too many XML nodes.
// *Action:  Increace the size of VM NodeStack in XmlXslVMCreate().
/
00690, 00000, "Invalid argument."
// *Cause:  Invalid or missing argument.
// *Action:  Check the function signature.
/
/ end of lpxus.msg
