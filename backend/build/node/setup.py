import os
from setuptools import (
    setup,
    find_packages,
)


version = '0.9.11'
shortdesc = "Building data structures as node trees"
longdesc = open(os.path.join(os.path.dirname(__file__), 'README.rst')).read()
longdesc += open(os.path.join(os.path.dirname(__file__), 'LICENSE.rst')).read()


setup(name='node',
      version=version,
      description=shortdesc,
      long_description=longdesc,
      classifiers=[
          'License :: OSI Approved :: BSD License',
          'Intended Audience :: Developers',
          'Topic :: Software Development',
          'Operating System :: OS Independent',
          'Programming Language :: Python',
      ],
      keywords='',
      author='BlueDynamics Alliance',
      author_email='dev@bluedynamics.com',
      url='http://github.com/bluedynamics/node',
      license='BSD',
      packages=find_packages('src'),
      package_dir={'': 'src'},
      namespace_packages=['node'],
      include_package_data=True,
      zip_safe=True,
      install_requires=[
          'setuptools',
          'odict>1.5',
          'plumber',
          'zope.lifecycleevent',
          'zope.deprecation',
      ],
      extras_require={
          'py24': [
              'uuid',
          ],
          'test': [
              'interlude',
              'plone.testing',
              'unittest2',
              'odict[test]',
              'plumber[test]',
          ]
      },
      entry_points="""
      """)
